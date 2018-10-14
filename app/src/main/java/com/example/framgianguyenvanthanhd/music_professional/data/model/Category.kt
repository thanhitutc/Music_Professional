package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by admin on 10/14/2018.
 */
data class Category (

    @SerializedName("idCategory")
    @Expose
    val idCategory: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("idTopic")
    @Expose
    val idTopic: String
)