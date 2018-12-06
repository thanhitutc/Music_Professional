package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment

import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.comment.Comment
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentRequest
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CommentRepository

/**
 * Created by admin on 12/6/2018.
 */
class CommentPresenter constructor(
        private val commentRepository: CommentRepository,
        private val view: CommentContract.CommentView
): CommentContract.CommentPresenter {

    override fun setView(view: CommentContract.CommentView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun fetchComment(idSong: String) {
        val commentRequest = CommentRequest(idSong = idSong)
        commentRepository.fetchComment(commentRequest, object : CommentDataSource.OnResponseFetchComment{
            override fun onSuccess(commentRequests: List<Comment>) {
                view.fetchCommentSuccess(commentRequests)
            }

            override fun onError() {
                view.postCommentError()
            }
        })
    }

    override fun postComment(idSong: String, content: String) {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        val commentRequest = CommentRequest(idSong, content, idAccount)
        commentRepository.postComment(commentRequest, object : CommentDataSource.OnResponsePostComment{
            override fun onSuccess() {
                view.postCommentSuccess()
            }

            override fun onError() {
                view.postCommentError()
            }
        })
    }
}