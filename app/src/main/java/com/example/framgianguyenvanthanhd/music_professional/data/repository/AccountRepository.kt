package com.example.framgianguyenvanthanhd.music_professional.data.repository

import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountRemoteDataSource

/**
 * Created by admin on 11/24/2018.
 */
class AccountRepository private constructor(
        private val remoteDataSource: AccountRemoteDataSource
): AccountDataSource {
    private object Holder {
        val INSTANCE = AccountRepository(
                AccountRemoteDataSource.getInstance())
    }

    companion object {
        @JvmStatic
        fun getInstance(): AccountRepository {
            return Holder.INSTANCE
        }
    }

    override fun register(account: Account, onResponse: AccountDataSource.OnResponseRegister) {
        remoteDataSource.register(account, onResponse)
    }

    override fun login(account: Account, onResponse: AccountDataSource.OnResponseLogin) {
        remoteDataSource.login(account, onResponse)
    }

    override fun updateUser(account: Account, onResponse: AccountDataSource.OnResponseLogin) {
        remoteDataSource.updateUser(account, onResponse)
    }
}