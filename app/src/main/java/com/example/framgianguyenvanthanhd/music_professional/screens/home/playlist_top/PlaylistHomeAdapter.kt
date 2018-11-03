package com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.squareup.picasso.Picasso

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 04/10/2018.
 */
class PlaylistHomeAdapter
(val playlists: List<Playlist>?,
 private val onItemPlaylistClick: OnItemPlaylistClick
 ) : RecyclerView.Adapter<PlaylistHomeAdapter.PlaylistHolder>() {

    interface OnItemPlaylistClick {

        fun onItemClick(playlist: Playlist)

    }

    override fun getItemCount(): Int {
        return playlists?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistHolder {
        val v: View = LayoutInflater.from(parent?.context).inflate(R.layout.item_playlist_home, parent, false)
        return PlaylistHolder(v)
    }

    override fun onBindViewHolder(holder: PlaylistHolder?, position: Int) {
        holder?.bindData(playlists?.get(position))
        holder?.itemView?.setOnClickListener{
            onItemPlaylistClick.onItemClick(playlists?.get(position)!!)
        }
    }


    class PlaylistHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val imgAvatarPl = itemView?.findViewById<ImageView>(R.id.img_playlist_home_image)
        private val imgBackgroundPl = itemView?.findViewById<ImageView>(R.id.img_playist_home_background)
        private val txtNamePl = itemView?.findViewById<TextView>(R.id.txt_playlist_home_name)

        fun bindData(playlist: Playlist?) {
            txtNamePl?.text = playlist?.name
            Picasso.with(itemView?.context).load(playlist?.image).into(imgAvatarPl)
            Picasso.with(itemView?.context).load(playlist?.background).into(imgBackgroundPl)
        }
    }
}