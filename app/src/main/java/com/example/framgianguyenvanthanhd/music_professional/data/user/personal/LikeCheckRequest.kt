package com.example.framgianguyenvanthanhd.music_professional.data.user.personal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 12/9/2018.
 */
data class LikeCheckRequest(

        @SerializedName("idAccount")
        @Expose
        val idAccount: String? = null,

        @SerializedName("idSong")
        @Expose
        val idSong: String? = null

)