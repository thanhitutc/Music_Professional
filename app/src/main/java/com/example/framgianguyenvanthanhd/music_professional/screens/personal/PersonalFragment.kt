package com.example.framgianguyenvanthanhd.music_professional.screens.personal

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainOfflineActivity
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainType
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.login.LoginFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.favorite_user.FavoritePersonalFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist.PlaylistPersonalFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_more_setting.*
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by admin on 11/10/2018.
 */
class PersonalFragment : BaseFragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_personal, container, false)
    }

    override fun initiateView() {
        mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(true)
        mainActivity.isDisplayToolbar(true)
        mainActivity.updateToolbar(true)
        val fistName = SharedPrefs.getInstance().get(KeysPref.FIRST_NAME.name, String::class.java)
        val lastName = SharedPrefs.getInstance().get(KeysPref.LAST_NAME.name, String::class.java)
        val avatar = SharedPrefs.getInstance().get(KeysPref.AVATAR.name, String::class.java)
        if (fistName.isNotEmpty() && lastName.isNotEmpty()) {
            txt_account_name.text = fistName + lastName
            menu_login?.isClickable = false
        } else if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isNotEmpty()) {
            txt_account_name.text = SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java)
            menu_login?.isClickable = false
        }
        if (avatar.isNotEmpty()) {
            Picasso.with(activity).load(avatar).into(account_avatar)
        }


        menu_login.setOnClickListener(this)
        menu_playlist.setOnClickListener(this)
        menu_songs.setOnClickListener(this)
        menu_download.setOnClickListener(this)
        menu_favorite.setOnClickListener(this)
        menu_favorite_online.setOnClickListener(this)
        menu_playlist_online.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.menu_login -> {
                if (txt_account_name.text != getString(R.string.login)) {
                    return
                }
                replaceFragment(LoginFragment())
                return
            }

            R.id.menu_favorite_online -> {
                if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isEmpty()) {
                    showDialogNeedLogin()
                    return
                }
                replaceFragment(FavoritePersonalFragment())
                return
            }
            R.id.menu_playlist_online -> {
                if (SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java).isEmpty()) {
                    showDialogNeedLogin()
                    return
                }
                replaceFragment(PlaylistPersonalFragment())
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

    private fun showDialogNeedLogin() {
        val buider = AlertDialog.Builder(context)
        buider.setMessage("Bạn cần đăng nhập để sử dụng tính năng này!")
        buider.setPositiveButton("OK") { dialog, _ ->
            replaceFragment(LoginFragment())
            dialog.dismiss()
        }
        buider.setNegativeButton("Huỷ") { dialog, _ ->
            dialog.dismiss()
        }
        buider.create().show()
    }
}
