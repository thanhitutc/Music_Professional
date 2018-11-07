package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by admin on 10/14/2018.
 */
data class Topic (
    @SerializedName("idTopic")
    @Expose
    val idTopic: String?,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("image")
    @Expose
    val image: String?
) : Serializable