package com.example.framgianguyenvanthanhd.music_professional.data.user.personal

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.OnResponseCommonSong
import com.example.framgianguyenvanthanhd.music_professional.Utils.ResponseType
import com.example.framgianguyenvanthanhd.music_professional.data.model.Song
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 12/9/2018.
 */
class PersonalRemoteLikeDataSource private constructor(
        private val service: PersonalApi = APIService.getPersonal()
) : PersonalLikeDataSource {
    private object Holder {
        val INSTANCE = PersonalRemoteLikeDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): PersonalRemoteLikeDataSource {
            return Holder.INSTANCE
        }
    }

    override fun checkLikeInfo(like: LikeRequest, onResponse: OnCommonResponse) {
        service.checkLikeInfo(like).enqueue(
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
                })
    }

    override fun getSongsLikeUser(like: LikeRequest, onResponseCommonSong: OnResponseCommonSong) {
        service.getLikeSongUser(like).enqueue(
                object : Callback<List<Song>> {

                    override fun onFailure(call: Call<List<Song>>?, t: Throwable?) {
                        onResponseCommonSong.onFailure()
                    }


                    override fun onResponse(call: Call<List<Song>>?, response: Response<List<Song>>?) {
                        if (response?.isSuccessful == true) {
                            response.body()?.let {
                                onResponseCommonSong.onSuccess(it)
                            }
                        } else {
                            onResponseCommonSong.onFailure()
                        }
                    }
                }
        )
    }
}