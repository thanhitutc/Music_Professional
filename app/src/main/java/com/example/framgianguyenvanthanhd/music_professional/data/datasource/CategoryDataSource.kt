package com.example.framgianguyenvanthanhd.music_professional.data.datasource

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Category

/**
 * Created by admin on 10/14/2018.
 */
interface CategoryDataSource {

    fun getCategory(onResponse: OnResponseCategory)

    fun getCategoryWithIdTopic(idTopic: String, onResponse: OnResponseCategory)

    fun getDetailCategoryId(id: String, onResponse: OnResponseDetailSong)

    interface OnResponseCategory {

        fun onSuccess(categorys: List<Category>?)

        fun onError(t: Throwable?)
    }
}