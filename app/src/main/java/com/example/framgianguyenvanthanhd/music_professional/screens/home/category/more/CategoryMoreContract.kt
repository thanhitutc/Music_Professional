package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.more

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category

/**
 * Created by admin on 11/7/2018.
 */
interface CategoryMoreContract {

    interface CategoryMoreView: BaseView<CategoryMorePresenter> {
        fun loadSuccessfully(list: List<Category>)

        fun loadError(t: Throwable)
    }

    interface CategoryMorePresenter: BasePresenter<CategoryMoreView> {

        fun fetchMoreCategory()
    }

}