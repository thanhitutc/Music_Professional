package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlayMostDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 10/27/2018.
 */
class PlayMostRemoteDataSource private constructor(
        private val dataService : DataService.PlayMostDataService? = APIService.getPlayMostAPI()
): PlayMostDataSource {

    private object Holder {
        val INSTANCE = PlayMostRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): PlayMostDataSource {
            return Holder.INSTANCE
        }
    }

    override fun fetchPlayMost(onResponse: PlayMostDataSource.OnResponsePlayMost) {
        dataService?.let {
            val callback: Call<List<SongHome>?> = dataService.getPlayMost()
            callback.enqueue(object : Callback<List<SongHome>?> {

                override fun onFailure(call: Call<List<SongHome>?>?, t: Throwable?) {
                    onResponse.onError(t)
                }

                override fun onResponse(call: Call<List<SongHome>?>?, response: Response<List<SongHome>?>?) {
                    if (response!!.isSuccessful) {
                        onResponse.onSuccess(response.body())
                    }
                }
            })
        }

    }

}