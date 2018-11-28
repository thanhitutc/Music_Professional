package com.example.framgianguyenvanthanhd.music_professional.screens.home.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.squareup.picasso.Picasso

/**
 * Created by admin on 10/27/2018.
 */
class SongHomeAdapter(
        private val songHomeSongs: List<SongHome>,
        private val isHomeScreen : Boolean,
        private val listener: OnItemSongHomeClickListener
): RecyclerView.Adapter<SongHomeAdapter.FavoriteHolder>() {

    interface OnItemSongHomeClickListener {

        fun onItemSongClick(song: SongHome)

        fun onMoreBtnClick(song: SongHome)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_rv, parent, false))
    }


    override fun getItemCount(): Int {
        return if (isHomeScreen) 5 else songHomeSongs.size
    }


    override fun onBindViewHolder(holder: FavoriteHolder?, position: Int) {
        holder?.binData(songHomeSongs[position])
        holder?.itemView?.setOnClickListener{
            listener.onItemSongClick(songHomeSongs[position])
        }
        holder?.moreSong?.setOnClickListener {
            listener.onMoreBtnClick(songHomeSongs[position])
        }
    }

    class FavoriteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val imageSong = itemView?.findViewById<ImageView>(R.id.song_image)
        private val nameSong = itemView?.findViewById<TextView>(R.id.song_name)
        private val singerSong = itemView?.findViewById<TextView>(R.id.song_singer)
        val moreSong = itemView?.findViewById<ImageView>(R.id.song_more)

        fun binData(songHome: SongHome) {
            Picasso.with(itemView?.context).load(songHome.image).into(imageSong)
            nameSong?.text = songHome.nameSong
            singerSong?.text = songHome.nameSinger
        }
    }
}