package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistPersonalRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalRequest

/**
 * Created by admin on 11/3/2018.
 */
class DetailPlaylistPresenter(
        private val repository: PlaylistHomeRepository,
        private val songParameterRepository: SongParameterRepository,
        private val songPlayingRepository: SongPlayingRepository,
        private val playlistPersonalRepository: PlaylistPersonalRepository,
        private val view: DetailPlaylistContract.DetailPlaylistView
) : DetailPlaylistContract.DetailPlPresenter {

    override fun setView(view: DetailPlaylistContract.DetailPlaylistView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchDetailPlaylist(id: String) {
        repository.getDetailPlaylistId(id, object : OnResponseDetailSong {
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

            }

            override fun onFail() {

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

    override fun deleteSongFromPlaylist(idSong: String, idPlaylist: String) {
        val request = PlaylistPersonalRequest(idPlaylist = idPlaylist, idSong = idSong )
        playlistPersonalRepository.deletSongFromPlaylist(request, object: OnCommonResponse {
            override fun onSuccess() {
                view.deleteSongSuccess(idSong)
            }

            override fun onFailure() {
                view.deleteSongFail()
            }
        })
    }
}
