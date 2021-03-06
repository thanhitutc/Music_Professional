package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.PlaylistHomeRemoteDataSource

/**
 * Created by admin on 10/2/2018.
 */
class PlaylistHomeRepository private constructor(playlistDataSource: PlaylistHomeDataSource): PlaylistHomeDataSource {

    override fun getDetailPlaylistId(id: String, onResponse: OnResponseDetailSong) {
        playlistHomeDataSource?.getDetailPlaylistId(id, onResponse)
    }

    override fun getPlaylistHome(onResponse: PlaylistHomeDataSource.OnResponsePlaylistHome) {
        playlistHomeDataSource?.getPlaylistHome(onResponse)
    }

    private var playlistHomeDataSource: PlaylistHomeDataSource? = null
    companion object {

        @JvmStatic
        fun getInstance() : PlaylistHomeRepository{
            return Holder.INSTANCE
        }
    }

    private object Holder {
        val INSTANCE = PlaylistHomeRepository(PlaylistHomeRemoteDataSource.getInstance())
    }





    init {
          playlistHomeDataSource = playlistDataSource
    }


}