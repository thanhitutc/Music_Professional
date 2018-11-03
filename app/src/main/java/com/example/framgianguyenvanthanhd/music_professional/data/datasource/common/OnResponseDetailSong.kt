package com.example.framgianguyenvanthanhd.music_professional.data.datasource.common

import com.example.framgianguyenvanthanhd.music_professional.data.model.Song

/**
 * Created by admin on 11/3/2018.
 */
interface OnResponseDetailSong {

    fun onSuccessfully(songs: List<Song>)

    fun onError(t: Throwable)
}