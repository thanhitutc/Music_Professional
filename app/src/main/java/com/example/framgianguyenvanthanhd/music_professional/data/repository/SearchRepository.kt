package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong
import com.example.framgianguyenvanthanhd.music_professional.data.search.SearchDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.search.SearchRemoteDataSource

/**
 * Created by admin on 12/16/2018.
 */
class SearchRepository private constructor(
        private val remoteDataSource: SearchRemoteDataSource
) : SearchDataSource {

    private object Holder {
        val INSTANCE = SearchRepository(
                SearchRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): SearchRepository {
            return Holder.INSTANCE
        }
    }

    override fun search(keywords: String, onResponse: OnResponseCommonSong) {
        return remoteDataSource.search(keywords, onResponse)
    }
}