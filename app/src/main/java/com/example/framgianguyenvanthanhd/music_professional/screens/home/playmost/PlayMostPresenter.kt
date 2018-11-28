package com.example.framgianguyenvanthanhd.music_professional.screens.home.playmost

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlayMostDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlayMostRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource

/**
 * Created by admin on 10/27/2018.
 */
class PlayMostPresenter(
        private val repository: PlayMostRepository,
        private val songParameterRepository: SongParameterRepository
): PlaymostContract.Presenter {
    private lateinit var view: PlaymostContract.View

    override fun setView(view: PlaymostContract.View) {
        this.view = view
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {
        TODO("not implemented")
    }

    override fun getPlayMostSongs() {
        repository.fetchPlayMost(object: PlayMostDataSource.OnResponsePlayMost{
            override fun onSuccess(songHomes: List<SongHome>?) {
                songHomes?.let {
                    view.playMostSongsSuccessfully(songHomes)
                }
            }

            override fun onError(t: Throwable?) {
                view.playMostSongsError(t)
            }
        })
    }

    override fun updateLikeSong(idSong: String) {
        songParameterRepository.updateLikeSong(idSong, object : SongParameterDataSource.OnResponseSongParameter{
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