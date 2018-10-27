package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.CategoryDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.CategoryRemoteDataSource

/**
 * Created by admin on 10/14/2018.
 */
class CategoryRepository private constructor(categoryDataSource: CategoryDataSource): CategoryDataSource {
    private val dataSource: CategoryDataSource

    init {
        dataSource = categoryDataSource
    }

    private object Holder {
        val INSTANCE = CategoryRepository(CategoryRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): CategoryRepository {
            return Holder.INSTANCE
        }
    }

    override fun getCategory(onResponse: CategoryDataSource.OnResponseCategory) {
        dataSource.getCategory(onResponse)
    }
}