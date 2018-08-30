package com.example.framgianguyenvanthanhd.music_professional.data

import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide

/**
 * Created by admin on 8/24/2018.
 */
interface SlideDataSource {

    fun getSlide(onResultGetSlide: OnResultGetSlide)

    interface OnResultGetSlide {
        fun onSuccess(slides: List<Slide>?)
        fun onFailure(t: Throwable?)
    }

}