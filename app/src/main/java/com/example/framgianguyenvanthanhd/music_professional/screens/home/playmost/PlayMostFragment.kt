package com.example.framgianguyenvanthanhd.music_professional.screens.home.playmost

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlayMostRepository
import kotlinx.android.synthetic.main.fragment_playmost.*

/**
 * Created by admin on 10/27/2018.
 */
class PlayMostFragment : Fragment(), PlaymostContract.View {
    private lateinit var presenter: PlaymostContract.Presenter
    private lateinit var adapter: PlayMostAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_playlist_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_playmost_home.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        presenter = PlayMostPresenter(PlayMostRepository.getInstance())
        presenter.setView(this)
        presenter.onStart()
        presenter.getPlayMostSongs()
    }

    override fun playMostSongsSuccessfully(songHomeSongs: List<SongHome>) {
        adapter = PlayMostAdapter(songHomeSongs)
        rc_playmost_home.adapter = adapter
    }

    override fun playMostSongsError(t: Throwable?) {
        TODO("not implemented")
    }

    override fun setPresenter(presenter: PlaymostContract.Presenter) {
        this.presenter = presenter
    }

}