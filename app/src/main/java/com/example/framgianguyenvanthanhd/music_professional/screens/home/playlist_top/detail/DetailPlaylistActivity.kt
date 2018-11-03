package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistHomeRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_songs.*


/**
 * Created by admin on 10/31/2018.
 */
class DetailPlaylistActivity : AppCompatActivity(), DetailPlaylistContract.DetailPlaylistView {

    private lateinit var presenter: DetailPlaylistContract.DetailPlPresenter

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
    }

    override fun setPresenter(presenter: DetailPlaylistContract.DetailPlPresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Song>) {

    }

    override fun loadError(t: Throwable) {

    }
}