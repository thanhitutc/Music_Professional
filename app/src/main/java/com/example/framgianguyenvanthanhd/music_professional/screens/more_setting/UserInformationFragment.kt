package com.example.framgianguyenvanthanhd.music_professional.screens.more_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_more_setting.*
import kotlinx.android.synthetic.main.fragment_personal.*
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * Created by admin on 12/25/2018.
 */
class UserInformationFragment : BaseFragment(), UserInfoContract.UserInfoView{

    private lateinit var presenter: UserInfoContract.UserInfoPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun initiateView() {
        presenter = UserInfoPresenter(AccountRepository.getInstance(), this)
        presenter.onStart()
        val fistName = SharedPrefs.getInstance().get(KeysPref.FIRST_NAME.name, String::class.java)
        val lastName = SharedPrefs.getInstance().get(KeysPref.LAST_NAME.name, String::class.java)
        val avatar = SharedPrefs.getInstance().get(KeysPref.AVATAR.name, String::class.java)
        val username = SharedPrefs.getInstance().get(KeysPref.USER_NAME.name, String::class.java)
        edt_firstname?.setText(fistName)
        edt_lastname?.setText(lastName)
        txt_username.text = username
        if (avatar.isNotEmpty()) {
            Picasso.with(activity).load(avatar).into(img_avatar)
        } else{
            Picasso.with(activity).load(R.drawable.ic_account).into(img_avatar)
        }

        btn_logout.setOnClickListener {
            SharedPrefs.getInstance().cleanUserInfo()
            (context as MainActivity).onBackPressed()
        }

        btn_change_info.setOnClickListener {
            val first = edt_firstname.text.toString()
            val last = edt_lastname.text.toString()
            val account = Account(firstName = first, lastName = last, userName = username)
            presenter.updateInfoUser(account)
            isloading_user_info?.visibility = View.VISIBLE
        }


    }

    override fun setPresenter(presenter: UserInfoContract.UserInfoPresenter) {
        this.presenter = presenter
    }

    override fun updateInfoSuccess(account: Account) {
        isloading_user_info?.visibility = View.INVISIBLE
    }

    override fun updateInfoFail() {
        isloading_user_info?.visibility = View.INVISIBLE
    }

    override fun updatePassSuccess() {
        isloading_user_info?.visibility = View.INVISIBLE
    }

    override fun updatePassFail() {
        isloading_user_info?.visibility = View.INVISIBLE
    }
}