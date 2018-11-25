package com.example.framgianguyenvanthanhd.music_professional.data.user

import com.example.framgianguyenvanthanhd.music_professional.Utils.ResponseType
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 11/24/2018.
 */
class AccountRemoteDataSource private constructor(
        private val service: AccountApi = APIService.getAccountApi()
) : AccountDataSource {
    private object Holder {
        val INSTANCE = AccountRemoteDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance(): AccountRemoteDataSource {
            return Holder.INSTANCE
        }
    }

    override fun register(account: Account, onResponse: AccountDataSource.OnResponseRegister) {
        service.register(account).enqueue(
                object : Callback<String> {

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        onResponse.onError()
                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true && response.body() == ResponseType.OK.name){
                            onResponse.onSuccess()
                        }else{
                            onResponse.onError()
                        }
                    }
                }
        )
    }

    override fun login(account: Account, onResponse: AccountDataSource.OnResponseLogin) {
        service.login(account).enqueue(
                object : Callback<Account> {

                    override fun onFailure(call: Call<Account>?, t: Throwable?) {
                        onResponse.onLoginFail()
                    }

                    override fun onResponse(call: Call<Account>?, response: Response<Account>?) {
                        if (response?.isSuccessful == true && response.body() != null) {
                            onResponse.onLoginSuccess(account)
                        } else {
                            onResponse.onLoginFail()
                        }
                    }

                }
        )
    }
}