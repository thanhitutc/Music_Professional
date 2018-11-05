package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository
import com.example.framgianguyenvanthanhd.music_professional.helper.GridSpacingItemDecoration
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.OnItemPlaylistClick
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song.DetailPlaylistActivity
import kotlinx.android.synthetic.main.layout_detail_grid.*

/**
 * Created by admin on 11/3/2018.
 */
class PlaylistMoreActivity : AppCompatActivity(), PlayMoreContract.PlayMoreView,
        OnItemPlaylistClick {

    private lateinit var presenter: PlayMoreContract.PlayMorePresenter
    private lateinit var adapter: PlayMoreAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context): Intent {
            return Intent(context, PlaylistMoreActivity::class.java)
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
        supportActionBar?.title = "Playlist"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24dp)
        toolbar_detail_grid.setNavigationOnClickListener { view ->
            finish()
        }
        rc_detail_grid.layoutManager = GridLayoutManager(this, 2)
        rc_detail_grid.addItemDecoration(GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(this, 10), true))
        presenter = PlayMorePresenter(PlaylistHomeRepository.getInstance(), this)
        presenter.setView(this)
        presenter.onStart()
        presenter.fetchMorePlaylist()
        progress_isloading_grid.visibility = View.VISIBLE
        swipe_refresh_grid.setOnRefreshListener {
            presenter.fetchMorePlaylist()
            swipe_refresh_grid.isRefreshing = true
        }
    }

    override fun setPresenter(presenter: PlayMoreContract.PlayMorePresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Playlist>) {
        progress_isloading_grid.visibility = View.INVISIBLE
        swipe_refresh_grid.isRefreshing = false
        adapter = PlayMoreAdapter(list, this)
        rc_detail_grid.adapter = adapter
    }

    override fun loadError(t: Throwable) {
        progress_isloading_grid.visibility = View.INVISIBLE
        swipe_refresh_grid.isRefreshing = false
    }

    override fun onItemClick(playlist: Playlist) {
        startActivity(DetailPlaylistActivity.getInstance(this, playlist))
    }
}