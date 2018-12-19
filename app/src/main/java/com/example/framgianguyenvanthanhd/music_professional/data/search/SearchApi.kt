package com.example.framgianguyenvanthanhd.music_professional.data.search

import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by admin on 12/16/2018.
 */
interface SearchApi {

    @POST("search.php")
    @FormUrlEncoded
    fun search(@Field("keywords") keywords: String): Call<List<Song>>

}