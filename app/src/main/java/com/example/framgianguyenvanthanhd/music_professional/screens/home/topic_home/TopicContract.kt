package com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic

/**
 * Created by admin on 10/14/2018.
 */
interface TopicContract {

    interface View : BaseView<Presenter> {

        fun topicsSuccessfully(topics: List<Topic>)

        fun topicError(t: Throwable?)
    }

    interface Presenter : BasePresenter<View> {

        fun getTopicsHome()
    }
}