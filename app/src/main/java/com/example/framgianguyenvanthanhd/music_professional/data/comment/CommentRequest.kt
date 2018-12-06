package com.example.framgianguyenvanthanhd.music_professional.data.comment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 12/5/2018.
 */
data class CommentRequest (

        @SerializedName("idSong")
        @Expose
        val idSong: String,

        @SerializedName("content")
        @Expose
        val content: String? = null,

        @SerializedName("idAccount")
        @Expose
        val idAccount: String? = null
)