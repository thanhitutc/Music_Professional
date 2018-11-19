package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.App
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.FavoriteLocalDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.resources.remote.FavoriteRemoteDataSource

/**
 * Created by admin on 10/27/2018.
 */
class FavoriteRepository private constructor(
        private val favoriteRemoteDataSource: FavoriteDataSource,
        private val favoriteLocalDataSource: FavoriteDataSource
) : FavoriteDataSource {

    private object Holder {
        val INSTANCE = FavoriteRepository(
                FavoriteRemoteDataSource.getInstance(),
                FavoriteLocalDataSource.getInstance(App.getContext()))
    }

    companion object {
        @JvmStatic
        fun getInstance(): FavoriteRepository {
            return Holder.INSTANCE
        }
    }

    override fun fetchFavoriteHome(onResponse: FavoriteDataSource.OnResponseFavoriteHome) {
        favoriteRemoteDataSource.fetchFavoriteHome(onResponse)
    }

    override fun getSongFavorite(type: Int): List<SongOffline> {
        return favoriteLocalDataSource.getSongFavorite(type)
    }

    override fun deleteFavorite(idSong: String): Boolean {
        return favoriteLocalDataSource.deleteFavorite(idSong)
    }

    override fun insertSongToFavorite(idSong: String): Boolean {
        return favoriteLocalDataSource.insertSongToFavorite(idSong)
    }

    override fun insertListSongToFavorite(songOfflines: List<SongOffline>?, callBack: FavoriteDataSource.CallBackInsertFavorite) {
        favoriteLocalDataSource.insertListSongToFavorite(songOfflines, callBack)
    }

}