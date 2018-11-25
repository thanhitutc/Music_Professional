package com.example.framgianguyenvanthanhd.music_professional.data.user

/**
 * Created by admin on 11/24/2018.
 */
interface AccountDataSource {

    fun register(account: Account, onResponse: OnResponseRegister)

    fun login(account: Account,onResponse: OnResponseLogin)

    interface OnResponseRegister {
        fun onSuccess()

        fun onError()
    }

    interface OnResponseLogin {
        fun onLoginSuccess(account: Account)

        fun onLoginFail()
    }
}