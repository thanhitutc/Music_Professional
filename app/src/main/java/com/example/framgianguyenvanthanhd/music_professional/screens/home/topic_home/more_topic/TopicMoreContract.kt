package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic

/**
 * Created by admin on 11/3/2018.
 */
interface TopicMoreContract {

    interface TopicMoreView : BaseView<TopicMorePresenter> {
        fun loadSuccessfully(list: List<Topic>)

        fun loadError(t: Throwable)
    }

    interface TopicMorePresenter : BasePresenter<TopicMoreView> {

        fun fetchMoreTopic()
    }
}