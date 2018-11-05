package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song

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
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.DetailSongAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_songs.*
import java.util.*


/**
 * Created by admin on 10/31/2018.
 */
class DetailPlaylistActivity : AppCompatActivity(), DetailPlaylistContract.DetailPlaylistView,
DetailSongAdapter.OnItemSongClickListener{

    private lateinit var presenter: DetailPlaylistContract.DetailPlPresenter
    private lateinit var adapter : DetailSongAdapter

    companion object {
        @JvmStatic
        fun getInstance(context: Context, playlist: Playlist): Intent {
            val intent = Intent(context, DetailPlaylistActivity::class.java)
            intent.action = Constants.PLAYLIST_ACTION
            intent.putExtra(Constants.PLAYLIST_EXTRA, playlist)
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
        presenter = DetailPlaylistPresenter(PlaylistHomeRepository.getInstance(), this)
        presenter.setView(this)
        presenter.onStart()
        val playlist: Playlist? = intent.getSerializableExtra(Constants.PLAYLIST_EXTRA) as Playlist
        playlist?.let {
            Picasso.with(baseContext).load(playlist.background).fit().into(backdrop)
            Picasso.with(baseContext).load(playlist.image).into(image_detail)
            collapsing_toolbar.title = playlist.name
            collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
            presenter.fetchDetailPlaylist(playlist.idPlaylist.orEmpty())
        }
        rc_detail_songs.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        rc_detail_songs.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            presenter.fetchDetailPlaylist(playlist?.idPlaylist.orEmpty())
        }
    }

    override fun setPresenter(presenter: DetailPlaylistContract.DetailPlPresenter) {
        this.presenter = presenter
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