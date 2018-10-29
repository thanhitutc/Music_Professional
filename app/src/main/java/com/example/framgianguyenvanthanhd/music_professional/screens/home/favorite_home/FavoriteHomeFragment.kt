package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteHomeFragment: Fragment(), FavoriteHomeContract.View {
    private lateinit var presenter: FavoriteHomeContract.Presenter
    private lateinit var adapter: FavoriteAdapter

    override fun setPresenter(presenter: FavoriteHomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun favoriteSongsSuccessfully(songHomeSongs: List<SongHome>) {
        adapter = FavoriteAdapter(songHomeSongs)
        rc_favorite_home.adapter = adapter
    }

    override fun favoriteSongsError(t: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_favorite_home.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        presenter = FavoriteHomePresenter(FavoriteRepository.getInstance())
        presenter.setView(this)
        presenter.onStart()
        presenter.getFavoriteSongs()
    }
}