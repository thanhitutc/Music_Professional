package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.ConfigCommonRetrofit
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.SlideDataSource

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */

class SlideRemoteDataSource private constructor() : SlideDataSource {
    init {
        slideData = ConfigCommonRetrofit.getSlideAPI()
    }

    private object Holder { val INSTANCE = SlideRemoteDataSource() }

    companion object {
        private var slideData: SlideDataSource? = null

        @JvmStatic
        fun getInstance(): SlideDataSource {
            return Holder.INSTANCE
        }
    }

    override fun getSlide(): Call<List<Slide>>? {
        val result = slideData?.getSlide()
        return null
    }
}
