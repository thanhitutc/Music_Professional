package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.comment.Comment
import com.squareup.picasso.Picasso

/**
 * Created by admin on 12/7/2018.
 */
class CommentAdapter(
        private val comments: MutableList<Comment>
) : RecyclerView.Adapter<CommentAdapter.CommentHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommentHolder {
        return CommentHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_comment, parent, false));
    }


    override fun getItemCount(): Int {
        return comments.size
    }


    override fun onBindViewHolder(holder: CommentHolder?, position: Int) {
        holder?.bindData(comments[position])
    }

    fun postComment(comment: Comment) {
        comments.add(comments.size, comment)
        notifyItemInserted(comments.size)
    }

    fun updatePostSuccess(time: String){
        comments[comments.size-1].CreatedAt = time
        notifyDataSetChanged()
    }

    class CommentHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val avatar = itemView?.findViewById<ImageView>(R.id.avatar)
        private val txtNameUserComment = itemView?.findViewById<TextView>(R.id.txt_name_user_comment)
        private val txtContent = itemView?.findViewById<TextView>(R.id.txt_content_comment)
        val createdAt = itemView?.findViewById<TextView>(R.id.createdAt)

        fun bindData(comment: Comment) {
            Picasso.with(itemView?.context).load(comment.avatar).into(avatar)
            val name = if (!comment.firstName.isNullOrBlank() && !comment.lastName.isNullOrBlank()){
                comment.firstName + comment.lastName
            } else {
                comment.userName
            }
            txtNameUserComment?.text = name
            txtContent?.text = comment.content
            createdAt?.text = comment.CreatedAt
        }
    }
}