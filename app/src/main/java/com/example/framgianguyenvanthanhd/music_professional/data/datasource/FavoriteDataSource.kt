package com.example.framgianguyenvanthanhd.music_professional.data.datasource

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongHome
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline

/**
 * Created by admin on 10/27/2018.
 */
interface FavoriteDataSource {

    fun fetchFavoriteHome(onResponse: OnResponseFavoriteHome)

    interface OnResponseFavoriteHome {

        fun onSuccess(songHomes: List<SongHome>?)

        fun onError(t: Throwable?)
    }

    /**
    * Offline
    * */

    fun getSongFavorite(type: Int): List<SongOffline>

    fun deleteFavorite(idSong: String): Boolean

    fun insertSongToFavorite(idSong: String): Boolean

    fun insertListSongToFavorite(songOfflines: List<SongOffline>?, callBack: CallBackInsertFavorite)

    /**
     * callback insert favorite
     */
    interface CallBackInsertFavorite {
        fun onComplete()

        fun onFail(songFail: List<String>)

        fun onInsertNoSong()
    }
}