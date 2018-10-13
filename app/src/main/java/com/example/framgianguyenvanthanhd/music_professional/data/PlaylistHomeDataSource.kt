package com.example.framgianguyenvanthanhd.music_professional.data

import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist

/**
 * Created by admin on 10/2/2018.
 */
interface PlaylistHomeDataSource {

    fun getPlaylistHome(onResponse: OnResponsePlaylistHome)

    interface OnResponsePlaylistHome {
        fun onSuccess(playlists: List<Playlist>?)
        fun onFailure(t: Throwable?)
    }
}