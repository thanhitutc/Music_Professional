package com.example.framgianguyenvanthanhd.music_professional.screens.personal.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import android.view.KeyEvent.KEYCODE_BACK
import android.R.attr.fragment
import android.view.KeyEvent
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.Utils.DialogUtils
import com.example.framgianguyenvanthanhd.music_professional.Utils.LoginType
import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.PersonalFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * Created by admin on 11/24/2018.
 */
class RegisterFragment : BaseFragment(), View.OnClickListener, RegisterContract.RegisterView {

    private lateinit var presenter: RegisterContract.RegisterPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_register, container, false)
    }

    override fun initiateView() {
        val mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(false)
        mainActivity.isDisplayToolbar(false)

        // detect button back
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { p0, keyCode, p2 ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                backFragment(LoginFragment())
                true
            } else false
        }

        presenter = RegisterPresenter(AccountRepository.getInstance(), this)
        presenter.setView(this)
        presenter.onStart()

        btn_register?.setOnClickListener(this)

    }


    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_register -> {
                if (edt_username.text.isEmpty() || edt_password.text.isEmpty() || edt_password_again.text.isEmpty()) {
                    DialogUtils.createDialogConfirm(
                            activity,
                            "Lỗi",
                            "Bạn chưa nhập đủ thông tin",
                            object:DialogUtils.OnDialogClick{
                                override fun onClickOk() {
                                    return
                                }

                                override fun onCancel() {

                                }
                            }
                            )
                    return
                }
                if (edt_password_again.text.toString() != edt_password.text.toString()) {
                    DialogUtils.createDialogConfirm(
                            activity,
                            "Lỗi",
                            "Mật khẩu nhập lại không đúng",
                            object:DialogUtils.OnDialogClick{
                                override fun onClickOk() {
                                    return
                                }

                                override fun onCancel() {

                                }
                            }
                    )
                    return
                }
                val accountRequest = Account(
                        userName = edt_username.text.toString(),
                        password = edt_password.text.toString(),
                        loginType = LoginType.NONE.value)
                presenter.register(accountRequest)
                isloading_register?.visibility = View.VISIBLE
            }
        }
    }

    override fun setPresenter(presenter: RegisterContract.RegisterPresenter) {
        this.presenter = presenter
    }

    override fun onRegisterSuccess() {
        isloading_register?.visibility = View.INVISIBLE
        Toast.makeText(activity,"Đăng ký thành công", Toast.LENGTH_SHORT).show()
        backFragment(LoginFragment())
    }

    override fun onRegisterError() {
        isloading_register?.visibility = View.INVISIBLE
        Toast.makeText(activity,"Đăng ký thất bại", Toast.LENGTH_SHORT).show()
    }
}
