package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable


/**
 * Created by admin on 10/2/2018.
 */
data class Playlist(
    @SerializedName("idPlaylist")
    @Expose
    val idPlaylist: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null,

    @SerializedName("background")
    @Expose
    val background: String? = null,

    @SerializedName("idAccount")
    @Expose
    val idAccount: String? = null
) : Serializable