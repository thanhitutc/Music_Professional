package com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistPersonalRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalRequest

/**
 * Created by admin on 12/22/2018.
 */
class PlaylistPersonalPresenter(
        private val playlistPersonalRepository: PlaylistPersonalRepository,
        private val view: PlaylistPersonalContract.PlaylistPersonalView
) : PlaylistPersonalContract.PlaylistPersonalPresenter {

    override fun setView(view: PlaylistPersonalContract.PlaylistPersonalView) {

    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchPlaylists() {
        val idAcc = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        val request = PlaylistPersonalRequest(idAccount = idAcc)
        playlistPersonalRepository.fetchPlaylists(request, object : PlaylistHomeDataSource.OnResponsePlaylistHome{
            override fun onSuccess(playlists: List<Playlist>?) {
                playlists?.let {
                    view.fetchPlaylistSuccess(playlists)
                } ?: view.fetchPlaylistsFail()
            }

            override fun onFailure(t: Throwable?) {
                view.fetchPlaylistsFail()
            }
        })
    }

    override fun createPlaylist(name: String) {
        val idAcc = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        val request = PlaylistPersonalRequest(idAccount = idAcc, namePlaylist = name)
        playlistPersonalRepository.createPlaylist(request, object : PlaylistPersonalDataSource.OnResponsePlaylistPersonal {
            override fun onSuccess(idPlaylist: String) {
                val playlist = Playlist(idPlaylist = idPlaylist, name = name)
                view.createPlaylistSuccess(playlist)
            }

            override fun onFailure() {
                view.createPlaylistFail()
            }
        })
    }

    override fun deletePlaylist(idPlaylist: String) {
        val request = PlaylistPersonalRequest(idPlaylist = idPlaylist)
        playlistPersonalRepository.deletePlaylist(request, object : PlaylistPersonalDataSource.OnResponsePlaylistPersonal {
            override fun onSuccess(idPl: String) {
                view.deletePlaylistSuccess(idPlaylist)
            }

            override fun onFailure() {
                view.deletePlaylistFail()
            }
        })
    }

    override fun deleteSongFromPlaylist(idSong: String, idPlaylist: String) {
        val request = PlaylistPersonalRequest(idSong = idSong, idPlaylist = idPlaylist)
        playlistPersonalRepository.deletSongFromPlaylist(request, object : OnCommonResponse{
            override fun onSuccess() {
                view.deleteSongPlaylistSuccess()
            }

            override fun onFailure() {
                view.deleteSongPlaylistFail()
            }
        })
    }

    override fun insertSongToPlaylist(idPlaylist: String, idSong: String) {
        val request = PlaylistPersonalRequest(idSong = idSong, idPlaylist = idPlaylist)
        playlistPersonalRepository.addSongToPlaylist(request, object : OnCommonResponse{
            override fun onSuccess() {
                view.insertSongSuccess()
            }

            override fun onFailure() {
                view.insertSongFail()
            }
        })
    }
}