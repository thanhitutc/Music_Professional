package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.comment.Comment

/**
 * Created by admin on 12/6/2018.
 */
interface PlayingMusicContract {

    interface PlayingView : BaseView<PlayingPresenter> {

        fun postCommentSuccess()

        fun postCommentError()

        fun fetchCommentSuccess(comments: List<Comment>)

        fun fetchCommentFail()

        fun checkLikeSuccess()

        fun checkLikeFailure()

        fun updateLikeSongSuccess()

        fun updateLikeSongFail()

    }

    interface PlayingPresenter : BasePresenter<PlayingView> {

        fun fetchComment(idSong: String)

        fun postComment(idSong: String, content: String)

        fun checkLikeSong(idSong: String)

        fun updateLikeSong(idSong: String, isLike: Boolean)
    }
}