package com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.ResponseType
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.PlaylistHomeDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 12/22/2018.
 */
class PlaylistPersonalRemoteDataSource private constructor(
        private val service: PlaylistPersonalApi = APIService.getPlaylistPersonal()
): PlaylistPersonalDataSource{

    private object Holder {
        val INSTANCE = PlaylistPersonalRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): PlaylistPersonalRemoteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun fetchPlaylists(request: PlaylistPersonalRequest, onResponse: PlaylistHomeDataSource.OnResponsePlaylistHome) {
        service.fetchPlaylists(request).enqueue(
                object : Callback<List<Playlist>> {

                    override fun onFailure(call: Call<List<Playlist>>?, t: Throwable?) {
                        onResponse.onFailure(t)
                    }

                    override fun onResponse(call: Call<List<Playlist>>?, response: Response<List<Playlist>>?) {
                        if (response?.isSuccessful == true && response.body() != null ){
                            onResponse.onSuccess(response.body())
                        } else {
                            onResponse.onFailure(null)
                        }
                    }
                }
        )
    }

    override fun createPlaylist(request: PlaylistPersonalRequest, onResponsePersonal: PlaylistPersonalDataSource.OnResponsePlaylistPersonal) {
        service.createPlaylist(request).enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponsePersonal.onFailure()
                    }


                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() != null) {
                            onResponsePersonal.onSuccess(response.body() ?: "-1")
                        } else {
                            onResponsePersonal.onFailure()
                        }
                    }
                }
        )
    }

    override fun deletePlaylist(request: PlaylistPersonalRequest, onResponse: PlaylistPersonalDataSource.OnResponsePlaylistPersonal) {
        service.deletePlaylist(request).enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onFailure()
                    }


                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name) {
                            onResponse.onSuccess("")
                        } else {
                            onResponse.onFailure()
                        }
                    }
                }
        )
    }

    override fun addSongToPlaylist(request: PlaylistPersonalRequest, onResponse: OnCommonResponse) {
        service.addSongToPlaylist(request).enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onFailure()
                    }


                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name) {
                            onResponse.onSuccess()
                        } else {
                            onResponse.onFailure()
                        }
                    }
                }
        )
    }

    override fun deletSongFromPlaylist(request: PlaylistPersonalRequest, onResponse: OnCommonResponse) {
        service.deleteSongFromPlaylist(request).enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onFailure()
                    }


                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name) {
                            onResponse.onSuccess()
                        } else {
                            onResponse.onFailure()
                        }
                    }
                }
        )
    }
}