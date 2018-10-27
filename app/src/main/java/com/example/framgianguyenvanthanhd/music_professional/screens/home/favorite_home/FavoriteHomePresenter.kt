package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteHomePresenter(
        private val repository: FavoriteRepository
): FavoriteHomeContract.Presenter {
    private lateinit var view: FavoriteHomeContract.View

    override fun setView(view: FavoriteHomeContract.View) {
        this.view = view
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {
        TODO("not implemented")
    }

    override fun getFavoriteSongs() {
        repository.fetchFavoriteHome(object : FavoriteDataSource.OnResponseFavoriteHome{
            override fun onSuccess(songHomes: List<SongHome>?) {
                songHomes?.let {
                    view.favoriteSongsSuccessfully(songHomes)
                }
            }

            override fun onError(t: Throwable?) {
                view.favoriteSongsError(t)
            }
        })
    }
}