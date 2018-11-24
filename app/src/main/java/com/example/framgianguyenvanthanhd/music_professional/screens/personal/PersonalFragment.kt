package com.example.framgianguyenvanthanhd.music_professional.screens.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainOfflineActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainType
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.login.LoginFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by admin on 11/10/2018.
 */
class PersonalFragment: BaseFragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_personal, container, false)
    }

    override fun initiateView() {
        mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(true)
        mainActivity.isDisplayToolbar(true)
//        mainActivity.setDefaultPersonalTab()

        menu_login.setOnClickListener(this)
        menu_playlist.setOnClickListener(this)
        menu_songs.setOnClickListener(this)
        menu_download.setOnClickListener(this)
        menu_favorite.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.menu_login -> {
                replaceFragment(LoginFragment())
                return
            }
        }

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