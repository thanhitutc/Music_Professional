package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalRemoteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalRequest

/**
 * Created by admin on 12/22/2018.
 */
class PlaylistPersonalRepository private constructor(
        private val remoteDataSource: PlaylistPersonalRemoteDataSource
) : PlaylistPersonalDataSource {


    private object Holder {
        val INSTANCE = PlaylistPersonalRepository(
                PlaylistPersonalRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): PlaylistPersonalRepository {
            return Holder.INSTANCE
        }
    }

    override fun fetchPlaylists(request: PlaylistPersonalRequest, onResponse: PlaylistHomeDataSource.OnResponsePlaylistHome) {
        remoteDataSource.fetchPlaylists(request, onResponse)
    }

    override fun createPlaylist(request: PlaylistPersonalRequest, onResponsePersonal: PlaylistPersonalDataSource.OnResponsePlaylistPersonal) {
        remoteDataSource.createPlaylist(request, onResponsePersonal)
    }

    override fun deletePlaylist(request: PlaylistPersonalRequest, onResponse: PlaylistPersonalDataSource.OnResponsePlaylistPersonal) {
        remoteDataSource.deletePlaylist(request, onResponse)
    }

    override fun addSongToPlaylist(request: PlaylistPersonalRequest, onResponse: OnCommonResponse) {
        remoteDataSource.addSongToPlaylist(request, onResponse)
    }

    override fun deletSongFromPlaylist(request: PlaylistPersonalRequest, onResponse: OnCommonResponse) {
        remoteDataSource.deletSongFromPlaylist(request, onResponse)
    }
}