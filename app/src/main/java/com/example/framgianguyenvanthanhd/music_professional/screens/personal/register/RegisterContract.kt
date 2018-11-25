package com.example.framgianguyenvanthanhd.music_professional.screens.personal.register

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account

/**
 * Created by admin on 11/24/2018.
 */
class RegisterContract {

    interface RegisterView : BaseView<RegisterPresenter> {
        fun onRegisterSuccess()

        fun onRegisterError()
    }

    interface RegisterPresenter : BasePresenter<RegisterView> {
        fun register(account: Account)
    }
}