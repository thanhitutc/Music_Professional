package com.example.framgianguyenvanthanhd.music_professional.screens.more_setting

import com.example.framgianguyenvanthanhd.music_professional.OnCommonResponse
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
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
                SharedPrefs.getInstance().put(KeysPref.ID_ACCOUNT.name, account.idAccount)
                SharedPrefs.getInstance().put(KeysPref.USER_NAME.name, account.userName)
                SharedPrefs.getInstance().put(KeysPref.LOGIN_TYPE.name, account.loginType)
                SharedPrefs.getInstance().put(KeysPref.FIRST_NAME.name, account.firstName)
                SharedPrefs.getInstance().put(KeysPref.LAST_NAME.name, account.lastName)
                SharedPrefs.getInstance().put(KeysPref.AVATAR.name, account.avatar)
                view.updateInfoSuccess(account)
            }

            override fun onLoginFail() {
                view.updateInfoFail()
            }
        })
    }

    override fun updatePassword(newPass: String) {
        val username = SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java)
        val account = Account(userName = username, password = newPass)
        userRepo.updatePassword(account, object : OnCommonResponse{
            override fun onSuccess() {
                view.updatePassSuccess()
            }

            override fun onFailure() {
                view.updatePassFail()
            }
        })
    }
}
