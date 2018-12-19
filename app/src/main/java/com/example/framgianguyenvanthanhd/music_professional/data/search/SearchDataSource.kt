package com.example.framgianguyenvanthanhd.music_professional.data.search

import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong

/**
 * Created by admin on 12/16/2018.
 */
interface SearchDataSource {

    fun search(keywords: String, onResponse: OnResponseCommonSong)

}