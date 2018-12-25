package com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.TopicType
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.category_from_topic.CategoryFromTopicActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home.more_topic.AllTopicActivity
import kotlinx.android.synthetic.main.fragment_topic_home.*

/**
 * Created by admin on 10/14/2018.
 */
class TopicHomeFragment : Fragment(), TopicContract.View , View.OnClickListener,
OnItemTopicClickListener{
    private lateinit var presenter: TopicContract.Presenter
    private lateinit var topicAdapter: TopicAdapter

    override fun setPresenter(presenter: TopicContract.Presenter) {
        this.presenter = presenter
    }

    override fun topicsSuccessfully(topics: List<Topic>) {
        topicAdapter = TopicAdapter(topics, TopicType.TOPIC_TOP, this)
        rc_topic_home?.let {
            it.layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            it.adapter = topicAdapter
        }
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
        txt_title_topic_home?.setOnClickListener(this)
        btn_topic_home_more?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.txt_title_topic_home -> context.startActivity(AllTopicActivity.getInstance(context))
            R.id.btn_topic_home_more -> context.startActivity(AllTopicActivity.getInstance(context))
        }
    }

    override fun onItemTopicClick(topic: Topic) {
        startActivity(CategoryFromTopicActivity.getInstance(context, topic))
    }
}
