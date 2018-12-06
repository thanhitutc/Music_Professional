package com.example.framgianguyenvanthanhd.music_professional.data.comment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 12/6/2018.
 */
data class Comment(

        @SerializedName("avatar")
        @Expose
        val avatar: String?,

        @SerializedName("username")
        @Expose
        val userName: String?,

        @SerializedName("firstName")
        @Expose
        val firstName: String?,

        @SerializedName("lastName")
        @Expose
        val lastName: String?,

        @SerializedName("content")
        @Expose
        val content: String?,

        @SerializedName("createdAt")
        @Expose
        var CreatedAt: String?
)