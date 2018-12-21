package com.example.framgianguyenvanthanhd.music_professional.data.user.personal

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong

/**
 * Created by admin on 12/9/2018.
 */
interface PersonalLikeDataSource {

    fun checkLikeInfo(like: LikeRequest, onResponse: OnCommonResponse)

    /*
    * body is idAccount
    * */
    fun getSongsLikeUser(like: LikeRequest, onResponseCommonSong: OnResponseCommonSong)
}