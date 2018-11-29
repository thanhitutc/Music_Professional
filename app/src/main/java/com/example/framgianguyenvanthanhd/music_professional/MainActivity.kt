package com.example.framgianguyenvanthanhd.music_professional

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.screens.OnUpdateDataPlayingListener
import com.example.framgianguyenvanthanhd.music_professional.screens.home.HomeFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.PersonalFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.PlayMusicActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_playing.*

class MainActivity : AppCompatActivity(), OnUpdateDataPlayingListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()

        initiateBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        initPlaying()
    }

    private fun initListener(){
        layout_playing_song.setOnClickListener(this)
    }

    private fun initiateBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_account -> {
                    initFragment(PersonalFragment())
                    true
                }
                R.id.menu_cd -> {
                    val homeFragment = HomeFragment()
                    initFragment(homeFragment)
                    true
                }
                R.id.menu_more -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
        bottom_navigation.selectedItemId = R.id.menu_account
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val frag = supportFragmentManager.findFragmentByTag(tag)
        if (frag != null && frag.isVisible) return

        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        }
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.main_containetr, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun initFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.main_containetr, fragment)
        ft.commit()
    }

    fun setDefaultPersonalTab() {
        bottom_navigation.selectedItemId = R.id.menu_account
    }

    fun isDisplayBottomNavigation(isDisplay: Boolean) {
        bottom_navigation.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    fun isDisplayToolbar(isDisplay: Boolean) {
        toolbar_main.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    private fun initPlaying() {
        val title = SharedPrefs.getInstance().get(KeysPref.NAME_PLAYING.name, String::class.java)
        val image = SharedPrefs.getInstance().get(KeysPref.IMAGE_PLAYING.name, String::class.java)
        val singer = SharedPrefs.getInstance().get(KeysPref.SINGER_PLAYING.name, String::class.java)
        val resource = SharedPrefs.getInstance().get(KeysPref.RESOURCE_PLAYING.name, String::class.java)
        if (title.isNotBlank()) {
            layout_playing_song.visibility = View.VISIBLE
            txt_song_playing.text = title
            txt_singer_playing.text = singer
            if (image.isBlank()){
                Picasso.with(this).load(R.drawable.ic_song).into(image_playing)
            } else {
                Picasso.with(this).load(image).into(image_playing)
            }
        }
    }

    override fun onUpdateSongPlaying(title: String, singer: String, image: String) {
        if (layout_playing_song.visibility != View.VISIBLE) {
            layout_playing_song.visibility = View.VISIBLE
        }
        txt_song_playing.text = title
        txt_singer_playing.text = singer
        Picasso.with(this).load(image).into(image_playing)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.layout_playing_song -> {
                startActivity(PlayMusicActivity.getInstance(this))
            }
        }
    }
}
