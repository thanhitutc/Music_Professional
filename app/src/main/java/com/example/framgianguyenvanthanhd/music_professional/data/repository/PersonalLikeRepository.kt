package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.LikeRequest
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.PersonalLikeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.PersonalRemoteLikeDataSource

/**
 * Created by admin on 12/9/2018.
 */
class PersonalLikeRepository private constructor(
        private val remoteDataSource: PersonalRemoteLikeDataSource
) : PersonalLikeDataSource {

    private object Holder {
        val INSTANCE = PersonalLikeRepository(
                PersonalRemoteLikeDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): PersonalLikeRepository {
            return Holder.INSTANCE
        }
    }

    override fun checkLikeInfo(like: LikeRequest, onResponse: OnCommonResponse) {
        return remoteDataSource.checkLikeInfo(like, onResponse)
    }

    override fun getSongsLikeUser(like: LikeRequest, onResponseCommonSong: OnResponseCommonSong) {
        return remoteDataSource.getSongsLikeUser(like, onResponseCommonSong)
    }
}