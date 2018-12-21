package com.example.framgianguyenvanthanhd.music_professional.data.user.personal

import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by admin on 12/9/2018.
 */
interface PersonalApi {

    @POST("personal.php")
    fun checkLikeInfo(@Body body: LikeRequest): Call<String>

    @POST("personal.php")
    fun getLikeSongUser(@Body body: LikeRequest): Call<List<Song>>
}