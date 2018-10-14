package com.example.framgianguyenvanthanhd.music_professional.data.datasource

import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic

/**
 * Created by admin on 10/14/2018.
 */
interface TopicDataSource {

    fun getTopicsHome(onResponse: OnResponseTopic)

    interface OnResponseTopic {

        fun onSuccess(topics: List<Topic>?)

        fun onError(t: Throwable?)
    }
}