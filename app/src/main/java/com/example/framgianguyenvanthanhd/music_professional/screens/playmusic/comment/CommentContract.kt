package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.comment.Comment

/**
 * Created by admin on 12/6/2018.
 */
interface CommentContract {

    interface CommentView : BaseView<CommentPresenter> {

        fun postCommentSuccess()

        fun postCommentError()

        fun fetchCommentSuccess(comments: List<Comment>)

        fun fetchCommentFail()

    }

    interface CommentPresenter : BasePresenter<CommentView> {

        fun fetchComment(idSong: String)

        fun postComment(idSong: String, content: String)
    }
}