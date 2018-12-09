package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteHomePresenter(
        private val repository: FavoriteRepository,
        private val songParameterRepository: SongParameterRepository
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

    override fun updateLikeSong(idSong: String) {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        songParameterRepository.updateLikeSong(idSong, idAccount, true, object : SongParameterDataSource.OnResponseSongParameter{
            override fun onSuccess() {

            }

            override fun onFail() {

            }
        })
    }

    override fun updatePlaySong(idSong: String) {
        songParameterRepository.updatePlaySong(idSong, object : SongParameterDataSource.OnResponseSongParameter{
            override fun onFail() {
            }

            override fun onSuccess() {

            }
        })
    }
}