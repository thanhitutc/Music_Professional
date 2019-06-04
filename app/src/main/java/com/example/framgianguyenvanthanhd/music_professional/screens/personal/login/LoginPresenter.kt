package com.example.framgianguyenvanthanhd.music_professional.screens.personal.login

import android.util.Log
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountDataSource

/**
 * Created by admin on 11/25/2018.
 */
class LoginPresenter(
        private val repository: AccountRepository,
        private val view: LoginContract.LoginView
) : LoginContract.LoginPresenter {

    override fun setView(view: LoginContract.LoginView) {

    }

    override fun onStart() {
        view.setPresenter(this)
    }

    override fun onStop() {

    }

    override fun login(account: Account) {
        repository.login(account, object : AccountDataSource.OnResponseLogin{
            override fun onLoginSuccess(account: Account) {
                Log.e("thanhd", account.toString())
                SharedPrefs.getInstance().put(KeysPref.ID_ACCOUNT.name, account.idAccount)
                SharedPrefs.getInstance().put(KeysPref.USER_NAME.name, account.userName)
                SharedPrefs.getInstance().put(KeysPref.PASS_USER.name, account.password)
                SharedPrefs.getInstance().put(KeysPref.LOGIN_TYPE.name, account.loginType)
                SharedPrefs.getInstance().put(KeysPref.FIRST_NAME.name, account.firstName)
                SharedPrefs.getInstance().put(KeysPref.LAST_NAME.name, account.lastName)
                SharedPrefs.getInstance().put(KeysPref.AVATAR.name, account.avatar)
                view.loginSuccess()
            }

            override fun onLoginFail() {
                view.loginFail()
            }
        })
    }
}
