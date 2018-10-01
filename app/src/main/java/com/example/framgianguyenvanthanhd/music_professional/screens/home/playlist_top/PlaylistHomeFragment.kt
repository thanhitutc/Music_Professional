package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.R
import kotlinx.android.synthetic.main.fragment_playlist_home.*

/**
 * Created by admin on 10/2/2018.
 */
class PlaylistHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_playlist_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val clickListener = View.OnClickListener {view ->

            when (view.getId()) {
                R.id.txt_title_playlist_home -> Toast.makeText(activity, "ok",Toast.LENGTH_LONG)
            }
        }
        txt_title_playlist_home.setOnClickListener(clickListener)
        super.onViewCreated(view, savedInstanceState)
    }
}


