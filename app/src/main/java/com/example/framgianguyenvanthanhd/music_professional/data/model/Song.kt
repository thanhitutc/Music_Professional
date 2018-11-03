package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 10/31/2018.
 */
data class Song (
        @SerializedName("idSong")
        @Expose
        val idSong: Int?,

        @SerializedName("name")
        @Expose
        val name: String?,

        @SerializedName("idSinger")
        @Expose
        val idSinger: Int?,

        @SerializedName("image")
        @Expose
        val image: String?,

        @SerializedName("link")
        @Expose
        val link: String?,

        @SerializedName("idAlbum")
        @Expose
        val idAlbum: Int?,

        @SerializedName("idGenres")
        @Expose
        val idGenres: Int?,

        @SerializedName("countLike")
        @Expose
        val countLike: Int?,

        @SerializedName("countPlay")
        @Expose
        val countPlay: Int?,

        @SerializedName("nameSinger")
        @Expose
        val nameSinger: String?
)