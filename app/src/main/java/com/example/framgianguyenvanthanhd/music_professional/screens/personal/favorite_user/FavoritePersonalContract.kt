package com.example.framgianguyenvanthanhd.music_professional.screens.personal.favorite_user

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song

/**
 * Created by admin on 12/19/2018.
 */
interface FavoritePersonalContract {

    interface FavPersonalView : BaseView<FavPersonalPresenter> {
        fun songsFavoriteSuccess(songs : List<Song>)

        fun songsFavoriteFail()

        fun removeFavoriteSuccess(idSong: String)

        fun removeFavoriteFail()
    }

    interface FavPersonalPresenter: BasePresenter<FavPersonalView> {
        fun getSongsFavorite()

        fun removeSongFavorite(idSong: String)
    }

}