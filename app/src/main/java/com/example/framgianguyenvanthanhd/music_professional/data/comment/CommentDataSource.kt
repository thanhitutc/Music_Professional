package com.example.framgianguyenvanthanhd.music_professional.data.comment

/**
 * Created by admin on 12/5/2018.
 */
interface CommentDataSource {

    fun postComment(commentRequest: CommentRequest, onResponse: OnResponsePostComment )

    fun fetchComment(commentRequest: CommentRequest, onResponse: OnResponseFetchComment)

    interface OnResponsePostComment {

        fun onSuccess()

        fun onError()
    }

    interface OnResponseFetchComment {

        fun onSuccess(commentRequests : List<Comment>)

        fun onError()
    }
}