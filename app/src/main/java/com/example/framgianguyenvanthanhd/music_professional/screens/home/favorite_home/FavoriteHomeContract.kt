package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Favorite

/**
 * Created by admin on 10/27/2018.
 */
interface FavoriteHomeContract {

    interface View : BaseView<Presenter> {

        fun favoriteSongsSuccessfully(favoriteSongs: List<Favorite>)

        fun favoriteSongs(t: Throwable?)
    }

    interface Presenter : BasePresenter<View> {

        fun getFavoriteSongs()
    }
}