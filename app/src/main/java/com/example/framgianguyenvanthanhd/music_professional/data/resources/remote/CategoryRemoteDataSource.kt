package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.CategoryDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 10/14/2018.
 */
class CategoryRemoteDataSource private constructor(): CategoryDataSource {

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
        callback?.enqueue(object: Callback<List<Category>>{

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
}