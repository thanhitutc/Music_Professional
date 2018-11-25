package com.example.framgianguyenvanthanhd.music_professional.screens.personal.register

import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountDataSource

/**
 * Created by admin on 11/24/2018.
 */
class RegisterPresenter(
        private val repository: AccountRepository,
        private val view: RegisterContract.RegisterView
) : RegisterContract.RegisterPresenter {

    override fun setView(view: RegisterContract.RegisterView) {
    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun register(account: Account) {
        repository.register(account, object : AccountDataSource.OnResponseRegister {
            override fun onSuccess() {
                view.onRegisterSuccess()
            }

            override fun onError() {
                view.onRegisterError()
            }
        })
    }
}