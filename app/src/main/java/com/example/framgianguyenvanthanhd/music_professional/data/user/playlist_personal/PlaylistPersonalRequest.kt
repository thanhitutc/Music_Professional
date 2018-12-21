package com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by admin on 12/22/2018.
 */
data class PlaylistPersonalRequest (

    @SerializedName("idPlaylist")
    @Expose
    val idPlaylist: String? = null,

    @SerializedName("idSong")
    @Expose
    val idSong: String? = null,

    @SerializedName("idAccount")
    @Expose
    val idAccount: String? = null,

    @SerializedName("namePlaylist")
    @Expose
    val namePlaylist: String? = null

)