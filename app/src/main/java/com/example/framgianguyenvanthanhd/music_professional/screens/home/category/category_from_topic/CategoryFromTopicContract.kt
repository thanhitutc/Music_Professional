package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.category_from_topic

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category

/**
 * Created by admin on 11/5/2018.
 */
interface CategoryFromTopicContract {
    interface CategoryFromTopicView : BaseView<CategoryFromTopicPresenter> {
        fun loadSuccessfully(list: List<Category>)

        fun loadError(t: Throwable)
    }

    interface CategoryFromTopicPresenter : BasePresenter<CategoryFromTopicView> {

        fun fetchCategoryFromTopic(idTopic: String)
    }
}