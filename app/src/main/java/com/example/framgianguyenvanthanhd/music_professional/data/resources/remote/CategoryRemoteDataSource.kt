package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.CategoryDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 10/14/2018.
 */
class CategoryRemoteDataSource private constructor() : CategoryDataSource {

    private val dataService: DataService.CategoryDataService

    init {
        dataService = APIService.getCategoryAPI()!!
    }

    private object Holder {
        val INSTANCE = CategoryRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): CategoryDataSource {
            return Holder.INSTANCE
        }
    }

    override fun getCategory(onResponse: CategoryDataSource.OnResponseCategory) {
        val callback: Call<List<Category>>? = dataService.getCategory()
        callback?.enqueue(object : Callback<List<Category>> {

            override fun onFailure(call: Call<List<Category>>?, t: Throwable?) {
                onResponse.onError(t)
            }

            override fun onResponse(call: Call<List<Category>>?, response: Response<List<Category>>?) {
                if (response!!.isSuccessful) {
                    onResponse.onSuccess(response.body())
                }
            }
        })
    }

    override fun getCategoryWithIdTopic(idTopic: String, onResponse: CategoryDataSource.OnResponseCategory) {
        val callback: Call<List<Category>> = dataService.getCategoryWithIdTopic(idTopic)
        callback.enqueue(object : Callback<List<Category>> {

            override fun onFailure(call: Call<List<Category>>?, t: Throwable?) {
                onResponse.onError(t)
            }


            override fun onResponse(call: Call<List<Category>>?, response: Response<List<Category>>?) {
                response?.let { response ->
                    if (response.isSuccessful) {
                        onResponse.onSuccess(response.body())
                    }
                }
            }
        })
    }

    override fun getDetailCategoryId(id: String, onResponse: OnResponseDetailSong) {
        val callback: Call<List<Song>?> = dataService.getDetailCategory(id)
        callback.enqueue(object : Callback<List<Song>?> {

            override fun onFailure(call: Call<List<Song>?>?, t: Throwable?) {
                t?.let {
                    onResponse.onError(t)
                }
            }

            override fun onResponse(call: Call<List<Song>?>?, response: Response<List<Song>?>?) {
                response?.body()?.let { response ->
                    onResponse.onSuccessfully(response)

                }
            }
        })
    }
}