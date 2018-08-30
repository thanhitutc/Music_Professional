package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.SlideDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.SlideRemoteDataSource

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */

class SlideRepository private constructor() : SlideDataSource {

    override fun getSlide(onResultGetSlide: SlideDataSource.OnResultGetSlide) {
        return Holder.slideDataSource.getSlide(onResultGetSlide)
    }


    init {
        Holder.slideDataSource = SlideRemoteDataSource.getInstance()
    }

    private object Holder {
        val INSTANCE = SlideRepository()
        lateinit var slideDataSource: SlideDataSource
    }

    companion object {
        @JvmStatic
        fun getInstance(): SlideRepository {
            return Holder.INSTANCE
        }
    }

}
