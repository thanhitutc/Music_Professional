package com.example.framgianguyenvanthanhd.music_professional.screens.personal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by admin on 11/10/2018.
 */
class PersonalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initView()
        return inflater?.inflate(R.layout.fragment_personal, container, false)
    }

    private fun initView() {
    }
}