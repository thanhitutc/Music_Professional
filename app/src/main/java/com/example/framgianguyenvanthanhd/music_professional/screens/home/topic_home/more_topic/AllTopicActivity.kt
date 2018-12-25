package com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home.more_topic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.TopicType
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.example.framgianguyenvanthanhd.music_professional.data.repository.TopicRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.category_from_topic.CategoryFromTopicActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail.TopicMoreContract
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail.TopicMorePresenter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home.OnItemTopicClickListener
import com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home.TopicAdapter
import kotlinx.android.synthetic.main.layout_detail_grid.*

/**
 * Created by admin on 11/5/2018.
 */
class AllTopicActivity : AppCompatActivity(), TopicMoreContract.TopicMoreView, OnItemTopicClickListener {
    private lateinit var presenter: TopicMoreContract.TopicMorePresenter
    private lateinit var adapter: TopicAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context): Intent {
            return Intent(context, AllTopicActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_grid)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar_detail_grid)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Topic"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24dp)
        toolbar_detail_grid.setNavigationOnClickListener { view ->
            finish()
        }
        rc_detail_grid?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        presenter = TopicMorePresenter(TopicRepository.getInstance(), this)
        presenter.setView(this)
        presenter.onStart()
        presenter.fetchMoreTopic()
        progress_isloading_grid?.visibility = View.VISIBLE
        swipe_refresh_grid?.setOnRefreshListener {
            presenter.fetchMoreTopic()
            swipe_refresh_grid?.isRefreshing = true
        }
    }

    override fun setPresenter(presenter: TopicMoreContract.TopicMorePresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Topic>) {
        progress_isloading_grid?.visibility = View.INVISIBLE
        swipe_refresh_grid?.isRefreshing = false
        adapter = TopicAdapter(list, TopicType.TOPIC_DETAIL, this)
        rc_detail_grid?.adapter = adapter
    }

    override fun loadError(t: Throwable) {
        progress_isloading_grid?.visibility = View.INVISIBLE
        swipe_refresh_grid?.isRefreshing = false
    }

    override fun onItemTopicClick(topic: Topic) {
        startActivity(CategoryFromTopicActivity.getInstance(this, topic))
    }
}
