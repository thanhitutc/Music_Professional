package com.example.framgianguyenvanthanhd.music_professional.screens.home.topic_home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
import com.squareup.picasso.Picasso

/**
 * Created by admin on 10/14/2018.
 */
class TopicAdapter(val topics: List<Topic>?) : RecyclerView.Adapter<TopicAdapter.TopicHolder>() {

    override fun onBindViewHolder(holder: TopicHolder?, position: Int) {
        holder?.binData(topics?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TopicHolder {
        return TopicHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_topic_category, parent, false))
    }

    override fun getItemCount(): Int {
        return topics?.size ?: 0
    }

    class TopicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val imgItem = itemView?.findViewById<ImageView>(R.id.img_topic_category)

        fun binData(topic: Topic?) {
            topic?.let {
                Picasso.with(itemView?.context).load(topic.image).into(imgItem)
            }
        }
    }
}