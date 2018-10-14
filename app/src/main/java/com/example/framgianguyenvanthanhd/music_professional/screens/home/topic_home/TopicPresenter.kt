package com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.TopicDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.example.framgianguyenvanthanhd.music_professional.data.repository.TopicRepository

/**
 * Created by admin on 10/14/2018.
 */
class TopicPresenter : TopicContract.Presenter {

    private lateinit var view: TopicContract.View
    private lateinit var repository: TopicRepository

    init {
        repository = TopicRepository.getInstance()
    }

    override fun setView(view: TopicContract.View) {
        this.view = view
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun getTopicsHome() {
        repository.getTopicsHome(object : TopicDataSource.OnResponseTopic {
            override fun onSuccess(topics: List<Topic>?) {
                topics?.let {
                    view.topicsSuccessfully(topics)
                }
            }

            override fun onError(t: Throwable?) {
                view.topicError(t)
            }
        })
    }
}