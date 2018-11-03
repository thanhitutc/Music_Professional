package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository

/**
 * Created by admin on 11/3/2018.
 */
class PlayMorePresenter(
        private val repository: PlaylistHomeRepository,
        private val view: PlayMoreContract.PlayMoreView
) : PlayMoreContract.PlayMorePresenter {

    override fun setView(view: PlayMoreContract.PlayMoreView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchMorePlaylist() {
        repository.getPlaylistHome(object : PlaylistHomeDataSource.OnResponsePlaylistHome {
            override fun onSuccess(playlists: List<Playlist>?) {
                playlists?.let {
                    view.loadSuccessfully(it)
                }
            }

            override fun onFailure(t: Throwable?) {
                view.loadError(t!!)
            }
        })
    }
}