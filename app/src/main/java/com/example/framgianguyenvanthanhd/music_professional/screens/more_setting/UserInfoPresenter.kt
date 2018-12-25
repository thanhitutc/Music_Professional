package com.example.framgianguyenvanthanhd.music_professional.screens.more_setting

import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountDataSource

/**
 * Created by admin on 12/26/2018.
 */
class UserInfoPresenter(
        private val userRepo: AccountRepository,
        private val view : UserInfoContract.UserInfoView
): UserInfoContract.UserInfoPresenter {

    override fun setView(view: UserInfoContract.UserInfoView) {

    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun updateInfoUser(account: Account) {
        userRepo.updateUser(account, object: AccountDataSource.OnResponseLogin{
            override fun onLoginSuccess(account: com.example.framgianguyenvanthanhd.music_professional.data.user.Account) {
                view.updateInfoSuccess(account)
            }

            override fun onLoginFail() {
                view.updateInfoFail()
            }
        })
    }

    override fun updatePassword(newPass: String) {

    }
}