package com.example.framgianguyenvanthanhd.music_professional.screens.personal.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.register.RegisterFragment
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by admin on 11/22/2018.
 */
class LoginFragment : BaseFragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun initiateView() {
        mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(false)
        mainActivity.isDisplayToolbar(false)
        txt_register.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
       when (p0?.id) {
           R.id.txt_register -> replaceFragment(RegisterFragment())
       }
    }
}