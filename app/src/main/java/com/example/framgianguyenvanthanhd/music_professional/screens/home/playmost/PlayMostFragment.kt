package com.example.framgianguyenvanthanhd.music_professional.screens.home.playmost

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.SongHomeDetailType
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlayMostRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.SongHomeAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail.SongHomeDetailActivity
import kotlinx.android.synthetic.main.fragment_playmost.*

/**
 * Created by admin on 10/27/2018.
 */
class PlayMostFragment : Fragment(), PlaymostContract.View, View.OnClickListener {
    private lateinit var presenter: PlaymostContract.Presenter
    private lateinit var adapter: SongHomeAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_playmost, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_playmost_home.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        presenter = PlayMostPresenter(PlayMostRepository.getInstance())
        presenter.setView(this)
        presenter.onStart()
        presenter.getPlayMostSongs()
        txt_title_playmost_home.setOnClickListener(this)
        btn_playmost_home_more.setOnClickListener(this)
    }

    override fun playMostSongsSuccessfully(songHomeSongs: List<SongHome>) {
        adapter = SongHomeAdapter(songHomeSongs, true)
        rc_playmost_home.adapter = adapter
    }

    override fun playMostSongsError(t: Throwable?) {
    }

    override fun setPresenter(presenter: PlaymostContract.Presenter) {
        this.presenter = presenter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.txt_title_playmost_home , R.id.btn_playmost_home_more ->
                context.startActivity(SongHomeDetailActivity.getInstance(context ,SongHomeDetailType.SONG_HOME_PLAY_MOST))
        }
    }
}