package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository

/**
 * Created by admin on 11/3/2018.
 */
class DetailPlaylistPresenter(
        private val repository: PlaylistHomeRepository,
        private val view: DetailPlaylistContract.DetailPlaylistView
): DetailPlaylistContract.DetailPlPresenter {

    override fun setView(view: DetailPlaylistContract.DetailPlaylistView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchDetailPlaylist(id: String) {
        repository.getDetailPlaylistId(id, object: OnResponseDetailSong{
            override fun onSuccessfully(songs: List<Song>) {
                view.loadSuccessfully(songs)
            }

            override fun onError(t: Throwable) {
                view.loadError(t)
            }
        })
    }
}