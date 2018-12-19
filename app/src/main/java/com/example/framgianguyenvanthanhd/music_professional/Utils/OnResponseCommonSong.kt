package com.example.framgianguyenvanthanhd.music_professional.Utils

import com.example.framgianguyenvanthanhd.music_professional.data.model.Song

/**
 * Created by admin on 12/16/2018.
 */
interface OnResponseCommonSong {

    fun onSuccess(songs: List<Song>)

    fun onFailure()

}