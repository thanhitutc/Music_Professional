package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.song_playing

import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying
import com.squareup.picasso.Picasso

/**
 * Created by admin on 11/3/2018.
 */
class PlayingSongAdapter(
        private var songs: MutableList<SongPlaying>,
        private var onItemSongClickListener: OnItemSongClickListener
) : RecyclerView.Adapter<PlayingSongAdapter.SongDetailHolder>() {


    interface OnItemSongClickListener {

        fun onItemSongClick(song: SongPlaying, positon: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SongDetailHolder {
        return SongDetailHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_rv, parent, false))
    }


    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SongDetailHolder?, position: Int) {
        holder?.bindData(songs[position])
        holder?.itemView?.setOnClickListener{
            onItemSongClickListener.onItemSongClick(songs[position], position)
        }
    }

    class SongDetailHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val imgSong = itemView?.findViewById<ImageView>(R.id.song_image)
        private val txtSongName = itemView?.findViewById<TextView>(R.id.song_name)
        private val txtSingerName = itemView?.findViewById<TextView>(R.id.song_singer)
        private val btnMore = itemView?.findViewById<ImageView>(R.id.song_more)

        fun bindData(song: SongPlaying) {
            song.image?.let {
                Picasso.with(itemView?.context).load(song.image).into(imgSong)
            }
            txtSongName?.text = song.name
            txtSingerName?.text = song.singer
        }

    }

    fun updateSongs(books: List<SongPlaying>) {
        if (songs.isNotEmpty()) {
            books.forEach { newSong ->
                // Remove duplicate post
                songs.removeAll { oldSong ->
                    oldSong.id == newSong.id
                }
            }
        }
        songs.addAll(books)
        notifyDataSetChanged()
    }

    fun getPositionSong(song: SongPlaying): Int {
        return songs.indexOf(song)
    }
}