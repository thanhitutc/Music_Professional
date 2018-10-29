package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlayMostDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.PlayMostRemoteDataSource

/**
 * Created by admin on 10/27/2018.
 */
class PlayMostRepository private constructor(
        private val playMostDataSource: PlayMostDataSource
): PlayMostDataSource{
    override fun fetchPlayMost(onResponse: PlayMostDataSource.OnResponsePlayMost) {
        playMostDataSource.fetchPlayMost(onResponse)
    }

    private object Holder {
        val INSTANCE = PlayMostRepository(PlayMostRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): PlayMostRepository {
            return Holder.INSTANCE
        }
    }
}