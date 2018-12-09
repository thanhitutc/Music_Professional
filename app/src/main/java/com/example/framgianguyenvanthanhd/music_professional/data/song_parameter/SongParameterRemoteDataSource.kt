package com.example.framgianguyenvanthanhd.music_professional.data.song_parameter

import com.example.framgianguyenvanthanhd.music_professional.Utils.ResponseType
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 11/27/2018.
 */
class SongParameterRemoteDataSource private constructor(
        private val service: SongParameterApi = APIService.getSongParameter()
) : SongParameterDataSource{

    private object Holder {
        val INSTANCE = SongParameterRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): SongParameterRemoteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun updateLikeSong(idSongLike: String,idAccount: String, onResponse: SongParameterDataSource.OnResponseSongParameter) {
        service.updateLikeSong(idSongLike, idAccount).enqueue(
                object: Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onFail()
                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name) {
                            onResponse.onSuccess()
                        }
                    }
                }
        )
    }

    override fun updatePlaySong(idPlaySong: String, onResponse: SongParameterDataSource.OnResponseSongParameter) {
        service.updatePlaySong(idPlaySong).enqueue(
                object: Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onFail()
                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name) {
                            onResponse.onSuccess()
                        }
                    }
                }
        )
    }
}