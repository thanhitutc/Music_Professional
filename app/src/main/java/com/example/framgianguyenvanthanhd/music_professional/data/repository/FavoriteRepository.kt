package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.FavoriteRemoteDataSource

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteRepository private constructor(
        private val favoriteDataSource: FavoriteDataSource
) : FavoriteDataSource {

    private object Holder {
        val INSTANCE = FavoriteRepository(FavoriteRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): FavoriteRepository {
            return Holder.INSTANCE
        }
    }

    override fun fetchFavoriteHome(onResponse: FavoriteDataSource.OnResponseFavoriteHome) {
        favoriteDataSource.fetchFavoriteHome(onResponse)
    }
}