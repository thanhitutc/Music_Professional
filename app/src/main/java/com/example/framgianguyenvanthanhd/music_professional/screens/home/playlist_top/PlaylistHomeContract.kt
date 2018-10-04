package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 04/10/2018.
 */
interface PlaylistHomeContract {

    interface PlaylistHomeView : BaseView<PlaylistHomePresenter> {
        fun loadSuccessfully(list: List<Playlist>?)

        fun loadError()

    }

    interface PlaylistHomePresenter : BasePresenter<PlaylistHomeView> {
        fun requestPlaylistsHome()
    }
}