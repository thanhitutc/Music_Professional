package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.comment.Comment
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentRequest
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CommentRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PersonalLikeRepository
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.LikeRequest

/**
 * Created by admin on 12/6/2018.
 */
class PlayingMusicPresenter constructor(
        private val commentRepository: CommentRepository,
        private val personalRepository: PersonalLikeRepository,
        private val songParameterRepository: SongParameterRepository,
        private val view: PlayingMusicContract.PlayingView
): PlayingMusicContract.PlayingPresenter {

    override fun setView(view: PlayingMusicContract.PlayingView) {
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

    override fun checkLikeSong(idSong: String) {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        val likeCheckRequest = LikeRequest(idAccount, idSong)
        personalRepository.checkLikeInfo(likeCheckRequest, object : OnCommonResponse{
            override fun onSuccess() {
                view.checkLikeSuccess()
            }

            override fun onFailure() {
                view.checkLikeFailure()
            }
        })
    }

    override fun updateLikeSong(idSong: String, isLike: Boolean) {
        val idAccount = SharedPrefs.getInstance().get(KeysPref.ID_ACCOUNT.name, String::class.java)
        songParameterRepository.updateLikeSong(idSong, idAccount, isLike, object: SongParameterDataSource.OnResponseSongParameter{
            override fun onSuccess() {
                view.updateLikeSongSuccess()
            }

            override fun onFail() {
                view.updateLikeSongFail()
            }
        })
    }
}