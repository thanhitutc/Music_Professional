package com.example.framgianguyenvanthanhd.music_professional.data.datasource

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome

/**
 * Created by admin on 10/27/2018.
 */
interface PlayMostDataSource {

    fun fetchPlayMost(onResponse: OnResponsePlayMost)

    interface OnResponsePlayMost {

        fun onSuccess(songHomes: List<SongHome>?)

        fun onError(t: Throwable?)
    }
}