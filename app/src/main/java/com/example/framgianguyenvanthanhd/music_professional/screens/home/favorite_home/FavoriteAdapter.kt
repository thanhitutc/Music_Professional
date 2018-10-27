package com.example.framgianguyenvanthanhd.music_professional.screens.home.favorite_home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.model.Favorite
import com.squareup.picasso.Picasso

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteAdapter(
        private val favoriteSongs: List<Favorite>
): RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_rv,parent, false))
    }


    override fun getItemCount(): Int = favoriteSongs.size


    override fun onBindViewHolder(holder: FavoriteHolder?, position: Int) {
        holder?.binData(favoriteSongs[position])
    }

    class FavoriteHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val imageSong = itemView?.findViewById<ImageView>(R.id.song_image)
        private val nameSong = itemView?.findViewById<TextView>(R.id.song_name)
        private val singerSong = itemView?.findViewById<TextView>(R.id.song_singer)
        private val moreSong = itemView?.findViewById<ImageView>(R.id.song_more)

        fun binData(favorite: Favorite) {
            Picasso.with(itemView?.context).load(favorite.image).into(imageSong)
            nameSong?.text = favorite.nameSong
            singerSong?.text = favorite.nameSinger
        }
    }
}