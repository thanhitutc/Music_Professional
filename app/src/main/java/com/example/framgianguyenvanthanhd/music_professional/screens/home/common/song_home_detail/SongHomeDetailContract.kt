package com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome

/**
 * Created by admin on 11/8/2018.
 */
interface SongHomeDetailContract {

    interface SongHomeDetailView : BaseView<SongHomeDetailPresenter> {

        fun loadSuccessfully(list: List<SongHome>)

        fun loadError(t: Throwable)

    }

    interface SongHomeDetailPresenter: BasePresenter<SongHomeDetailView> {

        fun fetchMostPlay()

        fun fetchFavorite()
    }
}