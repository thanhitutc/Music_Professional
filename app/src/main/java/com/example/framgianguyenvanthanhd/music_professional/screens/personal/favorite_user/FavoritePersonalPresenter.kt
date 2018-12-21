package com.example.framgianguyenvanthanhd.music_professional.screens.personal.favorite_user

import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PersonalLikeRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.LikeRequest

/**
 * Created by admin on 12/19/2018.
 */
class FavoritePersonalPresenter(
        private val likeRepository: PersonalLikeRepository,
        private val updateSongRepository: SongParameterRepository,
        private val view: FavoritePersonalContract.FavPersonalView
) : FavoritePersonalContract.FavPersonalPresenter {

    override fun setView(view: FavoritePersonalContract.FavPersonalView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun getSongsFavorite() {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        val likeRequest = LikeRequest(idAccount = idAccount)
        likeRepository.getSongsLikeUser(likeRequest, object : OnResponseCommonSong {
            override fun onSuccess(songs: List<Song>) {
                view.songsFavoriteSuccess(songs)
            }

            override fun onFailure() {
                view.songsFavoriteFail()
            }
        })
    }

    override fun removeSongFavorite(idSong: String) {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        updateSongRepository.updateLikeSong(idSong, idAccount, false, object : SongParameterDataSource.OnResponseSongParameter{
            override fun onSuccess() {
                view.removeFavoriteSuccess(idSong)
            }

            override fun onFail() {
                view.removeFavoriteFail()
            }
        })
    }
}