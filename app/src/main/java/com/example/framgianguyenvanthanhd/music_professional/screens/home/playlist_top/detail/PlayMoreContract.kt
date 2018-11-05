package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist

/**
 * Created by admin on 11/3/2018.
 */
interface PlayMoreContract {

    interface PlayMoreView: BaseView<PlayMorePresenter> {
        fun loadSuccessfully(list: List<Playlist>)

        fun loadError(t: Throwable)
    }

    interface PlayMorePresenter: BasePresenter<PlayMoreView> {

        fun fetchMorePlaylist()
    }
}