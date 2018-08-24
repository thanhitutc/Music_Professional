package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants

/**
 * Created by admin on 8/24/2018.
 */
class ConfigCommonRetrofit {

    companion object {
       fun getSlideAPI() : SlideDataSource? {
           return RetrofitClient.getClient(Constants.BASE_URL)?.create(SlideDataSource::class.java)
       }
    }
}