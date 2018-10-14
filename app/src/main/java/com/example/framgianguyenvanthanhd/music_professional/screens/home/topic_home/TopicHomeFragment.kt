package com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import kotlinx.android.synthetic.main.fragment_topic_home.*

/**
 * Created by admin on 10/14/2018.
 */
class TopicHomeFragment : Fragment(), TopicContract.View {
    private lateinit var presenter: TopicContract.Presenter
    private lateinit var topicAdapter: TopicAdapter

    override fun setPresenter(presenter: TopicContract.Presenter) {
        this.presenter = presenter
    }

    override fun topicsSuccessfully(topics: List<Topic>) {
        topicAdapter = TopicAdapter(topics)
        rc_topic_home.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
        rc_topic_home.adapter = topicAdapter
    }

    override fun topicError(t: Throwable?) {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_topic_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TopicPresenter()
        presenter.setView(this)
        presenter.onStart()
        presenter.getTopicsHome()
    }
}