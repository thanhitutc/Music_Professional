package com.example.framgianguyenvanthanhd.music_professional.screens.personal.playlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.OnItemPlaylistClick
import com.squareup.picasso.Picasso

/**
 * Created by admin on 12/22/2018.
 */
class PlaylistPersonalAdapter(
        private val playlists: MutableList<Playlist>,
        private val onItemPlaylistClick: OnItemPlaylistClick,
        private val onClickMore: OnClickPlaylistMoreButton
) : RecyclerView.Adapter<PlaylistPersonalAdapter.PlaylistHolder>() {

    interface OnClickPlaylistMoreButton {
        fun onClickMoreBtn(playlist: Playlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistHolder {
        return PlaylistHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_playlist_personal, parent, false))
    }


    override fun getItemCount(): Int {
        return playlists.size
    }

    fun addPlaylist(playlist: Playlist) {
        playlists.add(0,playlist)
        notifyItemInserted(0)
    }

    fun deletePlaylist(idPlaylist: String) {
        playlists.removeAll {  it.idPlaylist == idPlaylist}
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlaylistHolder?, position: Int) {
        holder?.bindData(playlists[position])
        holder?.itemView?.setOnClickListener {
            onItemPlaylistClick.onItemClick(playlists[position])
        }
        holder?.moreBtn?.setOnClickListener {
            onClickMore.onClickMoreBtn(playlists[position])
        }

    }

    inner class PlaylistHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val imgAvatar = itemView?.findViewById<ImageView>(R.id.img_avatar)
        private val txtName = itemView?.findViewById<TextView>(R.id.txt_name_playlist_personal)
        val moreBtn = itemView?.findViewById<ImageView>(R.id.btn_more)

        fun bindData(playlist: Playlist) {
            txtName?.text = playlist.name
            Picasso.with(itemView?.context).load(playlist.image).into(imgAvatar)
        }
    }
}