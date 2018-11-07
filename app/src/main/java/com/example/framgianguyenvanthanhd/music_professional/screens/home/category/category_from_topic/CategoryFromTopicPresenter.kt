package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.category_from_topic

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.CategoryDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository

/**
 * Created by admin on 11/5/2018.
 */
class CategoryFromTopicPresenter(
        private val repository: CategoryRepository,
        private val view: CategoryFromTopicContract.CategoryFromTopicView
): CategoryFromTopicContract.CategoryFromTopicPresenter {

    override fun setView(view: CategoryFromTopicContract.CategoryFromTopicView) {

    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun fetchCategoryFromTopic(idTopic: String) {
        repository.getCategoryWithIdTopic(idTopic, object : CategoryDataSource.OnResponseCategory{
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