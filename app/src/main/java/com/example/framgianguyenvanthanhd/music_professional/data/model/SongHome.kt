package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by admin on 10/27/2018.
 */
data class SongHome (
    @SerializedName("idSong")
    @Expose
    val idSong: String?,

    @SerializedName("nameSong")
    @Expose
    val nameSong: String?,

    @SerializedName("image")
    @Expose
    val image: String?,

    @SerializedName("link")
    @Expose
    val link: String?,

    @SerializedName("nameSinger")
    @Expose
    val nameSinger: String
)