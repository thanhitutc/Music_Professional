package com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.Utils.SongHomeDetailType
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlayMostRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.SongHomeAdapter
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_songs.*

/**
 * Created by admin on 11/8/2018.
 */
class SongHomeDetailActivity : AppCompatActivity(), SongHomeDetailContract.SongHomeDetailView,
SongHomeAdapter.OnItemSongHomeClickListener{

    private lateinit var presenter: SongHomeDetailContract.SongHomeDetailPresenter
    private lateinit var adapter: SongHomeAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context, typeSongHome: SongHomeDetailType): Intent {
            val intent = Intent(context, SongHomeDetailActivity::class.java)
            intent.putExtra(Constants.SONG_HOME_TYPE, typeSongHome.name)
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
        presenter = SongHomeDetailPresenter(
                FavoriteRepository.getInstance(),
                PlayMostRepository.getInstance(),
                this)
        presenter.setView(this)
        presenter.onStart()
        Picasso.with(baseContext).load(R.drawable.background_default).fit().into(backdrop)
        Picasso.with(baseContext).load(R.drawable.image_default).into(image_detail)
        var title: String = ""
        if (intent.getStringExtra(Constants.SONG_HOME_TYPE) == SongHomeDetailType.SONG_HOME_FAVORITE.name) {
            title = "Những ca khúc yêu thích nhất"
            presenter.fetchFavorite()
        } else {
            title = "Những ca khúc hot nhất"
            presenter.fetchMostPlay()
        }
        collapsing_toolbar.title = title
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
        rc_detail_songs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rc_detail_songs.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            if (intent.getStringExtra(Constants.SONG_HOME_TYPE) == SongHomeDetailType.SONG_HOME_FAVORITE.name) {
                presenter.fetchFavorite()
            } else {
                presenter.fetchMostPlay()
            }
        }
    }

    override fun setPresenter(presenter: SongHomeDetailContract.SongHomeDetailPresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<SongHome>) {
        progress_isloading.visibility = View.INVISIBLE
        swipe_refresh.isRefreshing = false
        adapter = SongHomeAdapter(list, false, this)
        rc_detail_songs.adapter = adapter
    }

    override fun loadError(t: Throwable) {
        progress_isloading.visibility = View.INVISIBLE
        swipe_refresh.isRefreshing = false
    }

    override fun onItemSongClick(song: SongHome) {

    }

    override fun onMoreBtnClick(song: SongHome) {
        val dialog = BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.menu_song_bottom_sheet)
                .setItemClickListener(BottomSheetItemClickListener { item->
                    when(item.itemId) {
                        R.id.menu_add_playing -> Log.e("thanhd", "Playing")
                        R.id.menu_like_song -> Log.e("thanhd", "Like")
                        R.id.menu_add_playlist -> Log.e("thanhd", "Play list")
                    }
                })
                .createDialog()

        dialog.show()
    }
}