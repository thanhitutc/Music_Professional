package com.example.framgianguyenvanthanhd.music_professional.screens.home.category.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.screens.home.category.OnItemCategoryClick
import com.squareup.picasso.Picasso

/**
 * Created by admin on 11/5/2018.
 */
class CategoryDetailAdapter(
        private val categorys: List<Category>,
        private val onItemClick: OnItemCategoryClick
) : RecyclerView.Adapter<CategoryDetailAdapter.CategoryFromTopicHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryFromTopicHolder {
        return CategoryFromTopicHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_detail_grid, parent, false))
    }


    override fun getItemCount(): Int {
        return categorys.size
    }


    override fun onBindViewHolder(holder: CategoryFromTopicHolder?, position: Int) {
        holder?.bindData(categorys[position])
        holder?.itemView?.setOnClickListener {
            onItemClick.onItemClick(categorys[position])
        }
    }


    class CategoryFromTopicHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val imgAvatar = itemView?.findViewById<ImageView>(R.id.img_avatar)
        private val txtName = itemView?.findViewById<TextView>(R.id.txt_name_detail)

        fun bindData(category: Category) {
            txtName?.text = category.name
            Picasso.with(itemView?.context).load(category.image).into(imgAvatar)
        }
    }
}