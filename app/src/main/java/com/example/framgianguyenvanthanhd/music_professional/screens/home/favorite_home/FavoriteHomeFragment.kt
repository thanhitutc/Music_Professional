package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.SongHomeDetailType
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.SongHomeAdapter
import com.example.framgianguyenvanthanhd.music_professional.screens.home.common.song_home_detail.SongHomeDetailActivity
import kotlinx.android.synthetic.main.fragment_favorite_home.*

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteHomeFragment: Fragment(), FavoriteHomeContract.View, View.OnClickListener {
    private lateinit var presenter: FavoriteHomeContract.Presenter
    private lateinit var mAdapter: SongHomeAdapter

    override fun setPresenter(presenter: FavoriteHomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun favoriteSongsSuccessfully(songHomeSongs: List<SongHome>) {
        mAdapter = SongHomeAdapter(songHomeSongs, true)
        rc_favorite_home.adapter = mAdapter
    }

    override fun favoriteSongsError(t: Throwable?) {
        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_favorite_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_favorite_home.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        presenter = FavoriteHomePresenter(FavoriteRepository.getInstance())
        presenter.setView(this)
        presenter.onStart()
        presenter.getFavoriteSongs()
        txt_title_favorite_home.setOnClickListener(this)
        btn_favorite_home_more.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.txt_title_favorite_home, R.id.btn_favorite_home_more ->
                    startActivity(SongHomeDetailActivity.getInstance(context,SongHomeDetailType.SONG_HOME_FAVORITE))
        }
    }
}