package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.TopicDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.example.framgianguyenvanthanhd.music_professional.data.repository.TopicRepository

/**
 * Created by admin on 11/3/2018.
 */
class TopicMorePresenter(
        private val repository: TopicRepository,
        private val view: TopicMoreContract.TopicMoreView
) : TopicMoreContract.TopicMorePresenter {

    override fun setView(view: TopicMoreContract.TopicMoreView) {
    }

    override fun fetchMoreTopic() {
        repository.getTopicsHome(object : TopicDataSource.OnResponseTopic{
            override fun onSuccess(topics: List<Topic>?) {
                topics?.let {
                    view.loadSuccessfully(it)
                }
            }

            override fun onError(t: Throwable?) {
                t?.let {
                    view.loadError(t)
                }
            }
        })
    }


    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

}