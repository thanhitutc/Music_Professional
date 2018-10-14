package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.SlideDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */

class SlideRemoteDataSource private constructor() : SlideDataSource {

    override fun getSlide(onResultGetSlide: SlideDataSource.OnResultGetSlide) {
        var callBack: Call<List<Slide>>? = slideDataService?.getSlide()
        callBack?.enqueue(object: Callback<List<Slide>> {
            override fun onResponse(call: Call<List<Slide>>?, response: Response<List<Slide>>?) {
                onResultGetSlide.onSuccess(response?.body())
            }

            override fun onFailure(call: Call<List<Slide>>?, t: Throwable?) {
                onResultGetSlide.onFailure(t)
            }
        })
    }

    init {
        slideDataService = APIService.getSlideAPI()

    }

    private object Holder {
        val INSTANCE = SlideRemoteDataSource()
    }

    companion object {
        private var slideDataService: DataService.SlideDataService? = null

        @JvmStatic
        fun getInstance(): SlideDataSource {
            return Holder.INSTANCE
        }
    }


}
