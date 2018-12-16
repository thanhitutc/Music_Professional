package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.LikeCheckRequest
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.PersonalDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.PersonalRemoteDataSource

/**
 * Created by admin on 12/9/2018.
 */
class PersonalRepository private constructor(
        private val remoteDataSource: PersonalRemoteDataSource
) : PersonalDataSource {

    private object Holder {
        val INSTANCE = PersonalRepository(
                PersonalRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): PersonalRepository {
            return Holder.INSTANCE
        }
    }

    override fun checkLikeInfo(likeCheck: LikeCheckRequest, onResponse: OnCommonResponse) {
        return remoteDataSource.checkLikeInfo(likeCheck, onResponse)
    }
}