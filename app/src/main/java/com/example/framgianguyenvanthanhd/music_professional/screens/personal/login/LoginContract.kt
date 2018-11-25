package com.example.framgianguyenvanthanhd.music_professional.screens.personal.login

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account

/**
 * Created by admin on 11/25/2018.
 */
interface LoginContract {
    interface LoginView : BaseView<LoginPresenter> {
        fun loginSuccess()

        fun loginFail()
    }

    interface LoginPresenter : BasePresenter<LoginView> {
        fun login(account: Account)
    }
}