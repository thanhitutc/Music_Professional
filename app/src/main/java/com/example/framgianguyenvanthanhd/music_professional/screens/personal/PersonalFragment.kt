package com.example.framgianguyenvanthanhd.music_professional.screens.personal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainOfflineActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainType
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by admin on 11/10/2018.
 */
class PersonalFragment: Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        menu_playlist.setOnClickListener(this)
        menu_songs.setOnClickListener(this)
        menu_download.setOnClickListener(this)
        menu_favorite.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val mainType = when (view?.id) {
            R.id.menu_playlist -> MainType.PLAYLIST
            R.id.menu_songs -> MainType.ALL_SONG
            R.id.menu_download -> MainType.DOWNLOAD
            R.id.menu_favorite -> MainType.FAVORITE
            else -> MainType.ALL_SONG
        }
        startActivity(MainOfflineActivity.getInstance(context, mainType))
    }
}