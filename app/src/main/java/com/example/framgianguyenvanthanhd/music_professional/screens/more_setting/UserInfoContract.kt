package com.example.framgianguyenvanthanhd.music_professional.screens.more_setting

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter
import com.example.framgianguyenvanthanhd.music_professional.BaseView
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account

/**
 * Created by admin on 12/26/2018.
 */
class UserInfoContract {

    interface UserInfoView : BaseView<UserInfoPresenter>{
        fun updateInfoSuccess(account: Account)

        fun updateInfoFail()

        fun updatePassSuccess()

        fun updatePassFail()
    }

    interface UserInfoPresenter: BasePresenter<UserInfoView>{
        fun updateInfoUser(account: Account)

        fun updatePassword(newPass: String)
    }
}