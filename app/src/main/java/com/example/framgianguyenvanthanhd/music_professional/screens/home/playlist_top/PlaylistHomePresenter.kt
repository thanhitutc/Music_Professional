package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top

import com.example.framgianguyenvanthanhd.music_professional.data.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 04/10/2018.
 */
class PlaylistHomePresenter : PlaylistHomeContract.PlaylistHomePresenter {
    private lateinit var view: PlaylistHomeContract.PlaylistHomeView
    private var repository: PlaylistHomeRepository

    init {
        repository = PlaylistHomeRepository.getInstance()
    }

    override fun setView(view: PlaylistHomeContract.PlaylistHomeView) {
        this.view = view
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {
    }

    override fun requestPlaylistsHome() {
        repository.getPlaylistHome(object : PlaylistHomeDataSource.OnResponsePlaylistHome {
            override fun onFailure(t: Throwable?) {
                view.loadError()
            }

            override fun onSuccess(playlists: List<Playlist>?) {
                view.loadSuccessfully(playlists)
            }

        })
    }
}