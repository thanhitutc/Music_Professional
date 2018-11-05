package com.example.framgianguyenvanthanhd.music_professional.screens.home.Category_home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.squareup.picasso.Picasso

/**
 * Created by admin on 10/14/2018.
 */
class CategoryAdapter(val Categorys: List<Category>?) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onBindViewHolder(holder: CategoryHolder?, position: Int) {
        holder?.binData(Categorys?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryHolder {
        return CategoryHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_topic_category, parent, false))
    }

    override fun getItemCount(): Int {
        return Categorys?.size ?: 0
    }

    class CategoryHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val imgItem = itemView?.findViewById<ImageView>(R.id.img_topic_category)

        fun binData(Category: Category?) {
            Category?.let {
                Picasso.with(itemView?.context).load(Category.image).fit().into(imgItem)
            }
        }
    }
}