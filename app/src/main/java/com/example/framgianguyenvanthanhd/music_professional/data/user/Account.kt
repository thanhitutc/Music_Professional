package com.example.framgianguyenvanthanhd.music_professional.data.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 11/24/2018.
 */
data class Account(

        @SerializedName("idAccount")
        @Expose
        val idAccount: String? = null,

        @SerializedName("username")
        @Expose
        val userName: String,

        @SerializedName("password")
        @Expose
        val password: String? = null,

        @SerializedName("idLoginProvider")
        @Expose
        val loginType: Int? = null,

        @SerializedName("firstName")
        @Expose
        val firstName: String? = null,

        @SerializedName("lastName")
        @Expose
        val lastName: String? = null,

        @SerializedName("avatar")
        @Expose
        val avatar: String? = null

)