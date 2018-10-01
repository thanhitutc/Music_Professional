package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.PlaylistHomeRemoteDataSource

/**
 * Created by admin on 10/2/2018.
 */
class PlaylistHomeRepository private constructor(playlistDataSource: PlaylistHomeDataSource): PlaylistHomeDataSource {
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



    override fun getPlaylistHome(): List<Playlist> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
          playlistHomeDataSource = playlistDataSource
    }


}