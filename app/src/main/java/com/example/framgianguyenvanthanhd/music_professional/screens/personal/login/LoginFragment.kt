package com.example.framgianguyenvanthanhd.music_professional.screens.personal.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.DialogUtils
import com.example.framgianguyenvanthanhd.music_professional.Utils.LoginType
import com.example.framgianguyenvanthanhd.music_professional.data.repository.AccountRepository
import com.example.framgianguyenvanthanhd.music_professional.data.user.Account
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.PersonalFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by admin on 11/22/2018.
 */
class LoginFragment : BaseFragment(), View.OnClickListener, LoginContract.LoginView {
    private lateinit var presenter: LoginContract.LoginPresenter
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun initiateView() {
        mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(false)
        mainActivity.isDisplayToolbar(false)
        txt_register?.setOnClickListener(this)
        btn_login?.setOnClickListener(this)

        presenter = LoginPresenter(AccountRepository.getInstance(), this)
        presenter.onStart()
        presenter.setView(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txt_register -> replaceFragment(RegisterFragment())
            R.id.btn_login -> {
                if (edt_username?.text.toString().isEmpty() || edt_password?.text.toString().isEmpty()) {
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
                    return
                }
                val account = Account(
                        userName = edt_username?.text.toString(),
                        password = edt_password?.text.toString(),
                        loginType = LoginType.NONE.value)
                presenter.login(account)
                isloading_login?.visibility = View.VISIBLE
            }
        }
    }

    override fun setPresenter(presenter: LoginContract.LoginPresenter) {
        this.presenter = presenter
    }

    override fun loginSuccess() {
        isloading_login?.visibility = View.INVISIBLE
        (context as MainActivity).onBackPressed()
    }

    override fun loginFail() {
        isloading_login?.visibility = View.INVISIBLE
        Toast.makeText(activity, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
    }
}
