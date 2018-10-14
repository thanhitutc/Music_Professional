package com.example.framgianguyenvanthanhd.music_professional.screens.home.Category_home

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category

/**
 * Created by admin on 10/14/2018.
 */
interface CategoryContract {

    interface View : BaseView<Presenter> {

        fun categorySuccessfully(Categorys: List<Category>)

        fun categoryError(t: Throwable?)
    }

    interface Presenter : BasePresenter<View> {

        fun getCategorysHome()
    }
}