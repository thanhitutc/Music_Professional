package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterRemoteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountRemoteDataSource

/**
 * Created by admin on 11/27/2018.
 */
class SongParameterRepository private constructor(
        private val songParameterRemoteDataSource: SongParameterRemoteDataSource
): SongParameterDataSource{

    private object Holder {
        val INSTANCE = SongParameterRepository(SongParameterRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): SongParameterRepository {
            return Holder.INSTANCE
        }
    }

    override fun updateLikeSong(idSongLike: String, onResponse: SongParameterDataSource.OnResponseSongParameter) {
        songParameterRemoteDataSource.updateLikeSong(idSongLike, onResponse)
    }

    override fun updatePlaySong(idPlaySong: String, onResponse: SongParameterDataSource.OnResponseSongParameter) {
        songParameterRemoteDataSource.updatePlaySong(idPlaySong, onResponse)
    }
}