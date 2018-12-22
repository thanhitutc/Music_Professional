package com.example.framgianguyenvanthanhd.music_professional.screens.home.playmost

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying

/**
 * Created by admin on 10/27/2018.
 */
interface PlaymostContract {

    interface View : BaseView<Presenter> {

        fun playMostSongsSuccessfully(songHomeSongs: List<SongHome>)

        fun playMostSongsError(t: Throwable?)
    }

    interface Presenter : BasePresenter<View> {

        fun getPlayMostSongs()

        fun updateLikeSong(idSong: String)

        fun updatePlaySong(idSong: String)

        fun insertToPlaying(songPlaying: SongPlaying)
    }
}