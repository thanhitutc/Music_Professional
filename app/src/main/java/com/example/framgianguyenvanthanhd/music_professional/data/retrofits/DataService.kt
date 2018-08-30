package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */
interface DataService {

    interface SlideDataService {
        @GET("Slide.php")
        fun getSlide() : Call<List<Slide>>?
    }
}