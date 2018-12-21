package com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource

/**
 * Created by admin on 12/22/2018.
 */
interface PlaylistPersonalDataSource {

    fun fetchPlaylists(request: PlaylistPersonalRequest,onResponse: PlaylistHomeDataSource.OnResponsePlaylistHome)

    fun createPlaylist(request: PlaylistPersonalRequest, onResponsePersonal: OnResponsePlaylistPersonal)

    fun deletePlaylist(request: PlaylistPersonalRequest, onResponse: OnResponsePlaylistPersonal)

    fun addSongToPlaylist(request: PlaylistPersonalRequest, onResponse: OnCommonResponse)

    fun deletSongFromPlaylist(request: PlaylistPersonalRequest, onResponse: OnCommonResponse)

    interface OnResponsePlaylistPersonal{
        fun onSuccess(idCreated : String)

        fun onFailure()
    }
}