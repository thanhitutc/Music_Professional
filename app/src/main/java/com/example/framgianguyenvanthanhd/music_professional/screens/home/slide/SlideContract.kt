package com.example.framgianguyenvanthanhd.music_professional.screens.home.slide

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide

/**
 * Created by admin on 8/30/2018.
 */
interface SlideContract {
    interface View : BaseView<Prensenter> {
        fun showSlidesSuccess(slides: List<Slide>)

        fun showSlidesFailure(t: Throwable)
    }

    interface Prensenter : BasePresenter<View> {
        fun requestGetSlides()
    }
}