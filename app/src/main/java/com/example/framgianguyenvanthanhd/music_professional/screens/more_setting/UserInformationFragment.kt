package com.example.framgianguyenvanthanhd.music_professional.screens.more_setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.DialogUtils
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs
import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_more_setting.*
import kotlinx.android.synthetic.main.fragment_personal.*
import kotlinx.android.synthetic.main.fragment_register.*
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
        val firstNameShow = if (fistName == "") {
            "N/A"
        } else {
            fistName
        }
        val lastnameShow = if (lastName == "") {
            "N/A"
        } else {
            lastName
        }
        edt_firstname?.setText(firstNameShow)
        edt_lastname?.setText(lastnameShow)
        txt_username?.text = username
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
            if (edt_new_pass.visibility != View.VISIBLE || edt_new_pass_again.visibility != View.VISIBLE) {
                val account = Account(firstName = first, lastName = last, userName = username)
                presenter.updateInfoUser(account)
            } else {
                if (edt_new_pass_again.text.isEmpty() || edt_new_pass.text.isEmpty() || edt_current_pass.text.isEmpty()) {
                    DialogUtils.createDialogConfirm(
                            activity,
                            "Lỗi",
                            "Bạn chưa nhập đủ thông tin",
                            object: DialogUtils.OnDialogClick{
                                override fun onClickOk() {
                                    return
                                }

                                override fun onCancel() {

                                }
                            }
                    )
                    return@setOnClickListener
                }
                Log.e("thanhd_pa", SharedPrefs.getInstance().get(KeysPref.PASS_USER.name, String::class.java))
                if (edt_current_pass.text.toString() != SharedPrefs.getInstance().get(KeysPref.PASS_USER.name, String::class.java)) {
                    DialogUtils.createDialogConfirm(
                            activity,
                            "Lỗi",
                            "Mật khẩu hiện tại chưa đúng.",
                            object: DialogUtils.OnDialogClick{
                                override fun onClickOk() {
                                    return
                                }

                                override fun onCancel() {

                                }
                            }
                    )
                    return@setOnClickListener
                }
                if (edt_new_pass.text.toString() != edt_new_pass_again.text.toString()) {
                    DialogUtils.createDialogConfirm(
                            activity,
                            "Lỗi",
                            "Mật khẩu nhập lại không đúng",
                            object: DialogUtils.OnDialogClick{
                                override fun onClickOk() {
                                    return
                                }

                                override fun onCancel() {

                                }
                            }
                    )
                    return@setOnClickListener
                }
                presenter.updatePassword(edt_new_pass_again.text.toString())
            }
            isloading_user_info?.visibility = View.VISIBLE
        }

        btn_change_pass.setOnClickListener {
            btn_change_pass?.visibility = View.GONE
            btn_logout?.visibility = View.GONE
            text_layout_current_pass?.visibility = View.VISIBLE
            text_layout_newpass?.visibility = View.VISIBLE
            text_layout_againpass?.visibility = View.VISIBLE
            edt_current_pass?.visibility = View.VISIBLE
            edt_new_pass?.visibility = View.VISIBLE
            edt_new_pass_again?.visibility = View.VISIBLE
            text_layout_firstname?.visibility = View.GONE
            text_layout_lastname?.visibility = View.GONE
            edt_firstname?.visibility = View.GONE
            edt_lastname?.visibility = View.GONE
        }
    }

    override fun setPresenter(presenter: UserInfoContract.UserInfoPresenter) {
        this.presenter = presenter
    }

    override fun updateInfoSuccess(account: Account) {
        isloading_user_info?.visibility = View.INVISIBLE
        (context as MainActivity).onBackPressed()
    }

    override fun updateInfoFail() {
        isloading_user_info?.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }

    override fun updatePassSuccess() {
        SharedPrefs.getInstance().put(KeysPref.PASS_USER.name, edt_new_pass.text.toString())
        isloading_user_info?.visibility = View.INVISIBLE
        (context as MainActivity).onBackPressed()
    }

    override fun updatePassFail() {
        isloading_user_info?.visibility = View.INVISIBLE
        Toasty.error(context, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show()
    }
}
