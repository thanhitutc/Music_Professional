package com.example.framgianguyenvanthanhd.music_professional.data.user.personal

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse

/**
 * Created by admin on 12/9/2018.
 */
interface PersonalDataSource {

    fun checkLikeInfo(likeCheck: LikeCheckRequest, onResponse: OnCommonResponse)

}