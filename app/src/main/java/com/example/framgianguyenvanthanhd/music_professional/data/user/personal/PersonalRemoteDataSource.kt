package com.example.framgianguyenvanthanhd.music_professional.data.user.personal

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.ResponseType
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 12/9/2018.
 */
class PersonalRemoteDataSource private constructor(
        private val service: PersonalApi = APIService.getPersonal()
) : PersonalDataSource {
    private object Holder {
        val INSTANCE = PersonalRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): PersonalRemoteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun checkLikeInfo(likeCheck: LikeCheckRequest, onResponse: OnCommonResponse) {
        service.checkLikeInfo(likeCheck).enqueue(
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
}