package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.more

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.CategoryDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository

/**
 * Created by admin on 11/7/2018.
 */
class CategoryMorePresenter(
        private val repository: CategoryRepository,
        private val view: CategoryMoreContract.CategoryMoreView
) : CategoryMoreContract.CategoryMorePresenter {
    override fun setView(view: CategoryMoreContract.CategoryMoreView) {

    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchMoreCategory() {
        repository.getCategory(object : CategoryDataSource.OnResponseCategory {
            override fun onSuccess(categorys: List<Category>?) {
                categorys?.let {
                    view.loadSuccessfully(it)
                }
            }

            override fun onError(t: Throwable?) {
                t?.let {
                    view.loadError(it)
                }
            }
        })
    }


}