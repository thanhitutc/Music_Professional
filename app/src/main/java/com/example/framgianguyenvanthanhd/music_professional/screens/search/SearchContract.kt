package com.example.framgianguyenvanthanhd.music_professional.screens.search

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song

/**
 * Created by admin on 12/16/2018.
 */
class SearchContract {
    interface SearchView : BaseView<SearchPresenter> {

        fun searchSuccess(songs: List<Song>)

        fun searchFail()

    }

    interface SearchPresenter : BasePresenter<SearchView> {

        fun search(keywords: String)

        fun updatePlaySong(idSong: String)
    }
}