package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying

/**
 * Created by admin on 11/6/2018.
 */
interface DetailSongsCategoryContract {
    interface DetailCategoryView : BaseView<DetailCategoryPresenter> {
        fun loadSuccessfully(list: List<Song>)

        fun loadError(t: Throwable)

        fun updateLikeSuccess()

        fun updateLikeFail()

    }

    interface DetailCategoryPresenter : BasePresenter<DetailCategoryView> {
        fun fetchDetailCategory(id: String)

        fun updateLikeSong(idSong: String)

        fun updatePlaySong(idSong: String)

        fun insertToPlaying(songPlaying: SongPlaying)
    }
}