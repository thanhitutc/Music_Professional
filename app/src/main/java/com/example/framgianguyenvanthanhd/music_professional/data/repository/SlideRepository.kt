package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.SlideDataSource

import retrofit2.Call

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */

class SlideRepository : SlideDataSource {

    init {
    }

    private object Holder { val INSTANCE = SlideRepository()}

    companion object {
        @JvmStatic
        fun getInstance() : SlideDataSource {
            return Holder.INSTANCE
        }
    }


    override fun getSlide(): Call<List<Slide>>? {
        return Holder.INSTANCE.getSlide()
    }




}
