package com.example.framgianguyenvanthanhd.music_professional.data.model

import com.example.framgianguyenvanthanhd.music_professional.Utils.SongMode
import java.io.Serializable

/**
 * Created by admin on 11/18/2018.
 */
data class SongPlaying(

        val id: String,

        val name: String,

        val singer: String,

        val image: String?,

        val resource: String,

        val mode: SongMode?

) : Serializable