package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.category_from_topic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository
import com.example.framgianguyenvanthanhd.music_professional.helper.GridSpacingItemDecoration
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.OnItemCategoryClick
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.adapter.CategoryDetailAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs.DetailSongsCategoryActivity
import kotlinx.android.synthetic.main.layout_detail_grid.*

/**
 * Created by admin on 11/5/2018.
 */
class CategoryFromTopicActivity : AppCompatActivity(), CategoryFromTopicContract.CategoryFromTopicView,
        OnItemCategoryClick {

    private lateinit var presenter: CategoryFromTopicContract.CategoryFromTopicPresenter
    private lateinit var mAdapter: CategoryDetailAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context, topic: Topic): Intent {
            val intent = Intent(context, CategoryFromTopicActivity::class.java)
            intent.putExtra(Constants.CATEGORY_FROM_TOPIC_EXTRA, topic)
            return intent
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
        supportActionBar?.title = "Category"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24dp)
        toolbar_detail_grid.setNavigationOnClickListener { view ->
            finish()
        }
        rc_detail_grid?.layoutManager = GridLayoutManager(this, 2)
        rc_detail_grid?.addItemDecoration(GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(this, 10), true))
        presenter = CategoryFromTopicPresenter(CategoryRepository.getInstance(), this)
        presenter.setView(this)
        presenter.onStart()
        val topic: Topic? = intent.getSerializableExtra(Constants.CATEGORY_FROM_TOPIC_EXTRA) as Topic
        topic?.idTopic?.let {
            presenter.fetchCategoryFromTopic(it)
        }
        progress_isloading_grid?.visibility = View.VISIBLE
        swipe_refresh_grid?.setOnRefreshListener {
            topic?.idTopic?.let {
                presenter.fetchCategoryFromTopic(it)
            }
            swipe_refresh_grid?.isRefreshing = true
        }
    }

    override fun setPresenter(presenter: CategoryFromTopicContract.CategoryFromTopicPresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Category>) {
        progress_isloading_grid?.visibility = View.INVISIBLE
        swipe_refresh_grid?.isRefreshing = false
        mAdapter = CategoryDetailAdapter(list, this)
        rc_detail_grid?.adapter = mAdapter
    }

    override fun loadError(t: Throwable) {
        progress_isloading_grid?.visibility = View.INVISIBLE
        swipe_refresh_grid?.isRefreshing = false
    }

    override fun onItemClick(category: Category) {
        startActivity(DetailSongsCategoryActivity.getInstance(this, category))
    }
}
