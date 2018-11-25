package com.example.framgianguyenvanthanhd.music_professional.data.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 11/24/2018.
 */
data class Account(

        @SerializedName("user_name")
        @Expose
        val userName: String,

        @SerializedName("pass_word")
        @Expose
        val password: String? = null,

        @SerializedName("login_type")
        @Expose
        val loginType: Int? = null,

        @SerializedName("first_name")
        @Expose
        val firstName: String? = null,

        @SerializedName("last_name")
        @Expose
        val lastName: String? = null,

        @SerializedName("avatar")
        @Expose
        val avatar: String? = null

)