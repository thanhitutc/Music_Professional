package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.more

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository
import com.example.framgianguyenvanthanhd.music_professional.helper.GridSpacingItemDecoration
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.OnItemCategoryClick
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.adapter.CategoryMoreAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs.DetailSongsCategoryActivity
import kotlinx.android.synthetic.main.layout_detail_grid.*

/**
 * Created by admin on 11/7/2018.
 */
class CategoryMoreActivity: AppCompatActivity(),
        CategoryMoreContract.CategoryMoreView, OnItemCategoryClick {

    private lateinit var presenter: CategoryMoreContract.CategoryMorePresenter
    private lateinit var adapter: CategoryMoreAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context): Intent {
            return Intent(context, CategoryMoreActivity::class.java)
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
        presenter = CategoryMorePresenter(CategoryRepository.getInstance(), this)
        presenter.setView(this)
        presenter.onStart()
        presenter.fetchMoreCategory()
        progress_isloading_grid?.visibility = View.VISIBLE
        swipe_refresh_grid?.setOnRefreshListener {
            presenter.fetchMoreCategory()
            swipe_refresh_grid?.isRefreshing = true
        }
    }

    override fun setPresenter(presenter: CategoryMoreContract.CategoryMorePresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Category>) {
        progress_isloading_grid?.visibility = View.INVISIBLE
        swipe_refresh_grid?.isRefreshing = false
        adapter = CategoryMoreAdapter(list, this)
        rc_detail_grid?.adapter = adapter
    }

    override fun loadError(t: Throwable) {
        progress_isloading_grid?.visibility = View.INVISIBLE
        swipe_refresh_grid?.isRefreshing = false
    }

    override fun onItemClick(category: Category) {
        startActivity(DetailSongsCategoryActivity.getInstance(this, category))
    }
}
