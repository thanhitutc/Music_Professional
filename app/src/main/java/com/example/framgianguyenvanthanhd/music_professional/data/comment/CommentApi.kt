package com.example.framgianguyenvanthanhd.music_professional.data.comment

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by admin on 12/5/2018.
 */
interface CommentApi {

    @POST("comment.php")
    fun postComment(@Body body: CommentRequest): Call<String>

    @POST("comment.php")
    fun fetchComments(@Body body: CommentRequest): Call<List<Comment>>
}