package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



/**
 * Created by admin on 10/2/2018.
 */
class Playlist {
    @SerializedName("idPlaylist")
    @Expose
    private val idPlaylist: String? = null
    @SerializedName("name")
    @Expose
    private val name: String? = null
    @SerializedName("image")
    @Expose
    private val image: String? = null
    @SerializedName("background")
    @Expose
    private val background: String? = null
    @SerializedName("idAccount")
    @Expose
    private val idAccount: String? = null
}