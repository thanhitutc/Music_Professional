package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.TopicDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.TopicRemoteDataSource

/**
 * Created by admin on 10/14/2018.
 */
class TopicRepository private constructor(topicDataSource: TopicDataSource) : TopicDataSource {

    private lateinit var dataSource: TopicDataSource

    init {
        dataSource = topicDataSource
    }

    private object Holder {
        val INSTANCE = TopicRepository(TopicRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): TopicRepository {
            return Holder.INSTANCE
        }
    }

    override fun getTopicsHome(onResponse: TopicDataSource.OnResponseTopic) {
        dataSource.getTopicsHome(onResponse)
    }
}