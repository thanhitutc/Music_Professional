package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by admin on 8/24/2018.
 */
interface SlideDataSource {

    @GET("Slide.php")
    fun getSlide() : Call<List<Slide>>?

}