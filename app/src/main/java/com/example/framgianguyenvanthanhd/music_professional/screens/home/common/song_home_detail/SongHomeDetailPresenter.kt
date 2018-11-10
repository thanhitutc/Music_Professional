package com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlayMostDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlayMostRepository

/**
 * Created by admin on 11/8/2018.
 */
class SongHomeDetailPresenter(
        private val favoriteRepository: FavoriteRepository,
        private val playMostRepository: PlayMostRepository,
        private val view: SongHomeDetailContract.SongHomeDetailView
): SongHomeDetailContract.SongHomeDetailPresenter {

    override fun setView(view: SongHomeDetailContract.SongHomeDetailView) {

    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchMostPlay() {
        playMostRepository.fetchPlayMost(object : PlayMostDataSource.OnResponsePlayMost{
            override fun onSuccess(songHomes: List<SongHome>?) {
                songHomes?.let {
                    view.loadSuccessfully(it)
                }
            }

            override fun onError(t: Throwable?) {
                t?.let {
                    view.loadError(it)
                }
            }
        })
    }

    override fun fetchFavorite() {
        favoriteRepository.fetchFavoriteHome(object : FavoriteDataSource.OnResponseFavoriteHome{
            override fun onSuccess(songHomes: List<SongHome>?) {
                songHomes?.let {
                    view.loadSuccessfully(it)
                }
            }

            override fun onError(t: Throwable?) {
                t?.let {
                    view.loadError(it)
                }
            }
        })
    }
}