package com.example.framgianguyenvanthanhd.music_professional.data.song_parameter

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by admin on 11/27/2018.
 */
interface SongParameterApi {

    @POST("update_song_parameter.php")
    @FormUrlEncoded
    fun updateLikeSong(@Field("idSongLike") idSong : String) : Call<String>

    @POST("update_song_parameter.php")
    @FormUrlEncoded
    fun updatePlaySong(@Field("idSongPlay") idSong : String) : Call<String>
}