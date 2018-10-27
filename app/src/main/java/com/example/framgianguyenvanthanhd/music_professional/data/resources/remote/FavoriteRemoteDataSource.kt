package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Favorite
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteRemoteDataSource private constructor(
        private val dataService: DataService.FavoriteDataService? = APIService.getFavoriteAPI()
) : FavoriteDataSource {

    private object Holder {
        val INSTANCE = FavoriteRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): FavoriteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun fetchFavoriteHome(onResponse: FavoriteDataSource.OnResponseFavoriteHome) {
        dataService?.let {
            val callback: Call<List<Favorite>?> = dataService.getFavorite()
            callback.enqueue(object : Callback<List<Favorite>?> {

                override fun onFailure(call: Call<List<Favorite>?>?, t: Throwable?) {
                    onResponse.onError(t)
                }

                override fun onResponse(call: Call<List<Favorite>?>?, response: Response<List<Favorite>?>?) {
                    if (response!!.isSuccessful) {
                        onResponse.onSuccess(response.body())
                    }
                }
            })
        }

    }
}