package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService

/**
 * Created by admin on 10/2/2018.
 */
class PlaylistHomeRemoteDataSource private constructor(): PlaylistHomeDataSource {

    private object Holder {
        val INSTANCE = PlaylistHomeRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): PlaylistHomeDataSource {
            return Holder.INSTANCE
        }
    }

    init {
        val dataService : DataService.PlaylistDataService? = APIService.getPlaylistAPI()
    }

    override fun getPlaylistHome(): List<Playlist> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}