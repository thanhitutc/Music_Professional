package com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist

/**
 * Created by admin on 12/22/2018.
 */
interface PlaylistPersonalContract {

    interface PlaylistPersonalView : BaseView<PlaylistPersonalPresenter>{
        fun fetchPlaylistSuccess(playlist: List<Playlist>)

        fun fetchPlaylistsFail()

        fun createPlaylistSuccess(playlist: Playlist)

        fun createPlaylistFail()

        fun deletePlaylistSuccess(idPlaylist: String)

        fun deletePlaylistFail()

        fun deleteSongPlaylistSuccess()

        fun deleteSongPlaylistFail()

        fun insertSongSuccess()

        fun insertSongFail()
    }

    interface PlaylistPersonalPresenter: BasePresenter<PlaylistPersonalView> {

        fun fetchPlaylists()

        fun createPlaylist(name: String)

        fun deletePlaylist(idPlaylist: String)

        fun deleteSongFromPlaylist(idSong : String, idPlaylist: String)

        fun insertSongToPlaylist(idPlaylist: String, idSong: String)
    }
}