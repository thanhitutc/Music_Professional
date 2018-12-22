package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs

import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource

/**
 * Created by admin on 11/6/2018.
 */
class DetailSongsCategoryPresenter(
        private val repository: CategoryRepository,
        private val songParameterRepository: SongParameterRepository,
        private val songPlayingRepository: SongPlayingRepository,
        private val view: DetailSongsCategoryContract.DetailCategoryView

) : DetailSongsCategoryContract.DetailCategoryPresenter {

    override fun setView(view: DetailSongsCategoryContract.DetailCategoryView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {
    }

    override fun fetchDetailCategory(id: String) {
        repository.getDetailCategoryId(id, object : OnResponseDetailSong {
            override fun onSuccessfully(songs: List<Song>) {
                view.loadSuccessfully(songs)
            }

            override fun onError(t: Throwable) {
                view.loadError(t)
            }
        })
    }

    override fun updateLikeSong(idSong: String) {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        songParameterRepository.updateLikeSong(idSong, idAccount, true, object : SongParameterDataSource.OnResponseSongParameter {
            override fun onSuccess() {
                view.updateLikeSuccess()
            }

            override fun onFail() {
                view.updateLikeFail()
            }
        })
    }

    override fun updatePlaySong(idSong: String) {
        songParameterRepository.updatePlaySong(idSong, object : SongParameterDataSource.OnResponseSongParameter {
            override fun onFail() {
            }

            override fun onSuccess() {

            }
        })
    }

    override fun insertToPlaying(songPlaying: SongPlaying) {
        songPlayingRepository.insertSongPlaying(songPlaying)
    }
}