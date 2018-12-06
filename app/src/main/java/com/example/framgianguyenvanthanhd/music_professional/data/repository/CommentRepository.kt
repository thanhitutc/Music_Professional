package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentRemoteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentRequest

/**
 * Created by admin on 12/6/2018.
 */
class CommentRepository private constructor(
        private val remoteDataSource: CommentRemoteDataSource
): CommentDataSource{

    private object Holder {
        val INSTANCE = CommentRepository(
                CommentRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): CommentRepository {
            return Holder.INSTANCE
        }
    }

    override fun postComment(commentRequest: CommentRequest, onResponse: CommentDataSource.OnResponsePostComment) {
        remoteDataSource.postComment(commentRequest, onResponse)
    }

    override fun fetchComment(commentRequest: CommentRequest, onResponse: CommentDataSource.OnResponseFetchComment) {
        remoteDataSource.fetchComment(commentRequest, onResponse)
    }
}