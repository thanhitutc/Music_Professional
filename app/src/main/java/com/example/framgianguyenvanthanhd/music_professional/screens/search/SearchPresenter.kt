package com.example.framgianguyenvanthanhd.music_professional.screens.search

import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SearchRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource

/**
 * Created by admin on 12/16/2018.
 */
class SearchPresenter(
        private val repository: SearchRepository,
        private val songParameterRepository: SongParameterRepository,
        private val view: SearchContract.SearchView
): SearchContract.SearchPresenter  {

    override fun setView(view: SearchContract.SearchView) {

    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun search(keywords: String) {
        repository.search(keywords, object : OnResponseCommonSong{
            override fun onSuccess(songs: List<Song>) {
                view.searchSuccess(songs)
            }

            override fun onFailure() {
                view.searchFail()
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
}