package com.example.framgianguyenvanthanhd.music_professional.data.search

import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountApi
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountRemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 12/16/2018.
 */
class SearchRemoteDataSource private constructor(
        private val service: SearchApi = APIService.getSearch()
):SearchDataSource{

    private object Holder {
        val INSTANCE = SearchRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): SearchRemoteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun search(keywords: String, onResponse: OnResponseCommonSong) {
        service.search(keywords).enqueue(
                object : Callback<List<Song>> {

                    override fun onFailure(call: Call<List<Song>>?, t: Throwable?) {
                        onResponse.onFailure()
                    }

                    override fun onResponse(call: Call<List<Song>>?, response: Response<List<Song>>?) {
                        if (response?.isSuccessful == true && response.body() != null) {
                            response.body()?.let {
                                onResponse.onSuccess(it)
                            }
                        } else {
                            onResponse.onFailure()
                        }
                    }
                }
        )
    }
}