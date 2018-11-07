package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository

/**
 * Created by admin on 11/6/2018.
 */
class DetailSongsCategoryPresenter(
        private val repository: CategoryRepository,
        private val view: DetailSongsCategoryContract.DetailCategoryView

): DetailSongsCategoryContract.DetailCategoryPresenter {

    override fun setView(view: DetailSongsCategoryContract.DetailCategoryView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {
    }

    override fun fetchDetailCategory(id: String) {
        repository.getDetailCategoryId(id, object : OnResponseDetailSong{
            override fun onSuccessfully(songs: List<Song>) {
                view.loadSuccessfully(songs)
            }

            override fun onError(t: Throwable) {
                view.loadError(t)
            }
        })
    }
}