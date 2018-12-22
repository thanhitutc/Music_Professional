package com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist.add_song

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.ID_SONG_ADD_PLAYLIST
import kotlinx.android.synthetic.main.activity_playlist_add.*

/**
 * Created by admin on 12/22/2018.
 */
class PlaylistForAddActivity : AppCompatActivity() {

    var idSongAddPlaylist: String = ""

    companion object {
        @JvmStatic
        fun getInstance(context: Context, idSongAddPlaylist: String): Intent {
            val intent = Intent(context, PlaylistForAddActivity::class.java)
            intent.putExtra(ID_SONG_ADD_PLAYLIST, idSongAddPlaylist)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_add)

        idSongAddPlaylist = intent.getStringExtra(ID_SONG_ADD_PLAYLIST)

        setSupportActionBar(toolbar_add_song_pl)
        supportActionBar?.title = getString(R.string.add_song_playlist)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}