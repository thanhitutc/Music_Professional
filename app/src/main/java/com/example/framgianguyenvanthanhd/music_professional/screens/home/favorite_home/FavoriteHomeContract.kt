package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome

/**
 * Created by admin on 10/27/2018.
 */
interface FavoriteHomeContract {

    interface View : BaseView<Presenter> {

        fun favoriteSongsSuccessfully(songHomeSongs: List<SongHome>)

        fun favoriteSongsError(t: Throwable?)
    }

    interface Presenter : BasePresenter<View> {

        fun getFavoriteSongs()

        fun updateLikeSong(idSong: String)

        fun updatePlaySong(idSong: String)
    }
}