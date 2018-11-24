package com.example.framgianguyenvanthanhd.music_professional.data.user

/**
 * Created by admin on 11/24/2018.
 */
interface AccountDataSource {

    fun register(account: Account, onResponse: OnResponseAccount)

    interface OnResponseAccount {
        fun onSuccess()

        fun onError()
    }
}