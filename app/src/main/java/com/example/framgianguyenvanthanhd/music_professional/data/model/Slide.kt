package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */

class Slide {
    @SerializedName("idSlide")
    @Expose
    var idSlide: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null
    @SerializedName("imageSlide")
    @Expose
    var imageSlide: String? = null
    @SerializedName("idSong")
    @Expose
    var idSong: String? = null
    @SerializedName("nameSong")
    @Expose
    var nameSong: String? = null
    @SerializedName("imageSong")
    @Expose
    var imageSong: String? = null
}
