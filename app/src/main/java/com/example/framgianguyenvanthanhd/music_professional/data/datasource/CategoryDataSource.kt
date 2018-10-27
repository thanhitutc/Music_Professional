package com.example.framgianguyenvanthanhd.music_professional.data.datasource

import com.example.framgianguyenvanthanhd.music_professional.data.model.Category

/**
 * Created by admin on 10/14/2018.
 */
interface CategoryDataSource {

    fun getCategory(onResponse: OnResponseCategory)

    interface OnResponseCategory {

        fun onSuccess(categorys: List<Category>?)

        fun onError(t: Throwable?)
    }
}