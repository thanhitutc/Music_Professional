package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.OnItemCategoryClick
import com.example.framgianguyenvanthanhd.music_professional.screens.home.playlist_top.OnItemPlaylistClick
import com.squareup.picasso.Picasso

/**
 * Created by admin on 11/3/2018.
 */
class CategoryMoreAdapter(
        private val category: List<Category>,
        private val onItemClick: OnItemCategoryClick
) : RecyclerView.Adapter<CategoryMoreAdapter.PlaylistMoreHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlaylistMoreHolder {
        return PlaylistMoreHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_detail_grid, parent, false))
    }


    override fun getItemCount(): Int {
        return category.size
    }


    override fun onBindViewHolder(holder: PlaylistMoreHolder?, position: Int) {
        holder?.bindData(category[position])
        holder?.itemView?.setOnClickListener {
            onItemClick.onItemClick(category[position])
        }
    }


    class PlaylistMoreHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val imgAvatar = itemView?.findViewById<ImageView>(R.id.img_avatar)
        private val txtName = itemView?.findViewById<TextView>(R.id.txt_name_detail)

        fun bindData(categor: Category) {
            txtName?.text = categor.name
            Picasso.with(itemView?.context).load(categor.image).into(imgAvatar)
        }
    }
}