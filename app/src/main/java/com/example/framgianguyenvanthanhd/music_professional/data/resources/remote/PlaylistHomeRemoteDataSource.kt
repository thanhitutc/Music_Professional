package com.example.framgianguyenvanthanhd.music_professional.data.resources.remote

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.OnResponseDetailSong
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.DataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 10/2/2018.
 */
class PlaylistHomeRemoteDataSource private constructor(): PlaylistHomeDataSource {

    override fun getDetailPlaylistId(id: String, onResponse: OnResponseDetailSong) {
        val callBack : Call<List<Song>?> = dataService?.getDetailPlaylist(id)!!
        callBack.enqueue(object : Callback<List<Song>?> {

            override fun onResponse(call: Call<List<Song>?>?, response: Response<List<Song>?>?) {
               if (response!!.isSuccessful) {
                   response.body()?.let {
                       onResponse.onSuccessfully(it)
                   }
               }
            }

            override fun onFailure(call: Call<List<Song>?>?, t: Throwable?) {
                onResponse.onError(t!!)
            }
        })
    }

    override fun getPlaylistHome(onResponse: PlaylistHomeDataSource.OnResponsePlaylistHome) {
        val callBack : Call<List<Playlist>>? = dataService?.getPlaylistHome()
        callBack?.enqueue(object : Callback<List<Playlist>> {
            override fun onFailure(call: Call<List<Playlist>>?, t: Throwable?) {
                onResponse.onFailure(t)
            }

            override fun onResponse(call: Call<List<Playlist>>?, response: Response<List<Playlist>>?) {
                onResponse.onSuccess(response?.body())
            }
        })
    }

    private var dataService: DataService.PlaylistDataService? = null
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
       dataService  = APIService.getPlaylistAPI()
    }


}