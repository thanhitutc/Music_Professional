package com.example.framgianguyenvanthanhd.music_professional.screens.home.playmost

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
class PlayMostAdapter(
        private val songHomeSongs: List<SongHome>
): RecyclerView.Adapter<PlayMostAdapter.FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_rv,parent, false))
    }


    override fun getItemCount(): Int = songHomeSongs.size


    override fun onBindViewHolder(holder: FavoriteHolder?, position: Int) {
        holder?.binData(songHomeSongs[position])
    }

    class FavoriteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val imageSong = itemView?.findViewById<ImageView>(R.id.song_image)
        private val nameSong = itemView?.findViewById<TextView>(R.id.song_name)
        private val singerSong = itemView?.findViewById<TextView>(R.id.song_singer)
        private val moreSong = itemView?.findViewById<ImageView>(R.id.song_more)

        fun binData(songHome: SongHome) {
            Picasso.with(itemView?.context).load(songHome.image).into(imageSong)
            nameSong?.text = songHome.nameSong
            singerSong?.text = songHome.nameSinger
        }
    }
}