package com.example.framgianguyenvanthanhd.music_professional.screens.personal.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.MainActivity
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.screens.BaseFragment

/**
 * Created by admin on 11/22/2018.
 */
class LoginFragment : BaseFragment() {
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun initiateView() {
        mainActivity = activity as MainActivity
        mainActivity.isDisplayBottomNavigation(false)
        mainActivity.isDisplayToolbar(false)
    }
}