package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by admin on 10/14/2018.
 */
data class Topic (
    @SerializedName("idTopic")
    @Expose
    var idTopic: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("image")
    @Expose
    var image: String? = null
)