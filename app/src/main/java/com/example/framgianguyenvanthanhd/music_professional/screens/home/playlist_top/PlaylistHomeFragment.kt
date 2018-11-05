package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail.PlaylistMoreActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.detail_song.DetailPlaylistActivity
import kotlinx.android.synthetic.main.fragment_playlist_home.*

/**
 * Created by admin on 10/2/2018.
 */
class PlaylistHomeFragment : Fragment(), PlaylistHomeContract.PlaylistHomeView,
       OnItemPlaylistClick, View.OnClickListener {

    private lateinit var presenter: PlaylistHomeContract.PlaylistHomePresenter
    private lateinit var plHomeAdapter: PlaylistHomeAdapter

    override fun setPresenter(presenter: PlaylistHomeContract.PlaylistHomePresenter) {
        this.presenter = presenter
    }

    override fun loadSuccessfully(list: List<Playlist>?) {
        plHomeAdapter = PlaylistHomeAdapter(list, this)
        rc_playlist_home.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        rc_playlist_home.adapter = plHomeAdapter
    }

    override fun loadError() {
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_playlist_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter = PlaylistHomePresenter()
        presenter.setView(this)
        presenter.onStart()
        presenter.requestPlaylistsHome()
        txt_title_playlist_home.setOnClickListener(this)
        btn_playlist_home_more.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.txt_title_playlist_home -> context.startActivity(PlaylistMoreActivity.getInstance(context))
            R.id.btn_playlist_home_more -> context.startActivity(PlaylistMoreActivity.getInstance(context))
        }
    }

    override fun onItemClick(playlist: Playlist) {
        activity.startActivity(DetailPlaylistActivity.getInstance(context, playlist))
    }
}


