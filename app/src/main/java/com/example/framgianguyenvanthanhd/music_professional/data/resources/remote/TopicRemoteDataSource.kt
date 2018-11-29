package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.TopicDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 10/14/2018.
 */
class TopicRemoteDataSource private constructor() : TopicDataSource {

    private lateinit var dataService: DataService.TopicDataService

    init {
        dataService = APIService.getTopicAPI()!!
    }

    private object Holder {
        val INSTANCE = TopicRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): TopicDataSource {
            return Holder.INSTANCE
        }
    }

    override fun getTopicsHome(onResponse: TopicDataSource.OnResponseTopic) {
        val call: Call<List<Topic>>? = dataService.getTopics()
        call?.enqueue(object : Callback<List<Topic>> {
            override fun onFailure(call: Call<List<Topic>>?, t: Throwable?) {
                onResponse.onError(t)
            }

            override fun onResponse(call: Call<List<Topic>>?, response: Response<List<Topic>>?) {
                if (response?.isSuccessful == true && response?.body() != null) {
                    onResponse.onSuccess(response.body())
                }
            }
        })
    }
}