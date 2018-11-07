package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song

/**
 * Created by admin on 11/6/2018.
 */
interface DetailSongsCategoryContract {
    interface DetailCategoryView : BaseView<DetailCategoryPresenter> {
        fun loadSuccessfully(list: List<Song>)

        fun loadError(t: Throwable)

    }

    interface DetailCategoryPresenter : BasePresenter<DetailCategoryView> {
        fun fetchDetailCategory(id: String)
    }
}