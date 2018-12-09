package com.example.framgianguyenvanthanhd.music_professional.data.song_parameter

/**
 * Created by admin on 11/27/2018.
 */
interface SongParameterDataSource {

    fun updateLikeSong(idSongLike : String,idAccount: String, onResponse: OnResponseSongParameter)

    fun updatePlaySong(idPlaySong : String, onResponse: OnResponseSongParameter)

    interface OnResponseSongParameter {

        fun onSuccess()

        fun onFail()
    }
}