package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song

/**
 * Created by admin on 11/3/2018.
 */
interface DetailPlaylistContract {

    interface DetailPlaylistView : BaseView<DetailPlPresenter> {
        fun loadSuccessfully(list: List<Song>)

        fun loadError(t: Throwable)

    }

    interface DetailPlPresenter : BasePresenter<DetailPlaylistView> {
        fun fetchDetailPlaylist(id: String)

        fun updateLikeSong(idSong: String)

        fun updatePlaySong(idSong: String)
    }
}