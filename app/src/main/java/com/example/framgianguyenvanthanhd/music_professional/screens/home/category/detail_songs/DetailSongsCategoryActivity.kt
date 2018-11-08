package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.detail_songs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CategoryRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.DetailSongAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_songs.*

/**
 * Created by admin on 11/6/2018.
 */
class DetailSongsCategoryActivity: AppCompatActivity(), DetailSongsCategoryContract.DetailCategoryView,
DetailSongAdapter.OnItemSongClickListener{

    private lateinit var mPresenter: DetailSongsCategoryContract.DetailCategoryPresenter
    private lateinit var adapter : DetailSongAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context, category: Category): Intent {
            val intent = Intent(context, DetailSongsCategoryActivity::class.java)
            intent.putExtra(Constants.CATEGORY_ID_EXTRA, category)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_songs)

        initView()
        initData()
    }

    @SuppressLint("ResourceType")
    private fun initView() {
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24dp)
        toolbar_detail.setNavigationOnClickListener { view ->
            finish()
        }
        collapsing_toolbar.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar.setCollapsedTitleTextAppearance(Color.WHITE)
    }

    private fun initData() {
        mPresenter = DetailSongsCategoryPresenter(CategoryRepository.getInstance(), this)
        mPresenter.setView(this)
        mPresenter.onStart()
        val category: Category? = intent.getSerializableExtra(Constants.CATEGORY_ID_EXTRA) as Category
        category?.let {
            Picasso.with(baseContext).load(category.image).fit().into(backdrop)
            Picasso.with(baseContext).load(category.image).into(image_detail)
            collapsing_toolbar.title = category.name
            collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
            mPresenter.fetchDetailCategory(category.idCategory)
        }
        rc_detail_songs.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        rc_detail_songs.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            category?.idCategory?.let {
                mPresenter.fetchDetailCategory(it)
            }
        }
    }

    override fun setPresenter(presenter: DetailSongsCategoryContract.DetailCategoryPresenter) {
        this.mPresenter = presenter
    }

    override fun loadSuccessfully(list: List<Song>) {
        progress_isloading.visibility = View.INVISIBLE
        swipe_refresh.isRefreshing = false
        adapter = DetailSongAdapter(list, this)
        rc_detail_songs.adapter = adapter
    }

    override fun loadError(t: Throwable) {
        swipe_refresh.isRefreshing = false
        progress_isloading.visibility = View.INVISIBLE
    }

    override fun onItemSongClick(song: Song) {

    }
}