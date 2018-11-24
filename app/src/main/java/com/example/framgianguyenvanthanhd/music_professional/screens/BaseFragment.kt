package com.example.framgianguyenvanthanhd.music_professional.screens

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.R

/**
 * Created by admin on 11/22/2018.
 */
abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateView()
    }

    abstract fun initiateView()

    protected fun replaceFragment(fragment: Fragment) {
        val f = activity.supportFragmentManager
                .findFragmentByTag(fragment.javaClass.name)
        if (f != null && f == fragment) {
            if (fragment.isVisible) {
                return
            }
            activity.supportFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
            return
        }

        activity.supportFragmentManager.popBackStack()
        activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.anim_enter_right, R.anim.anim_exit_left)
                .replace(R.id.main_containetr, fragment, fragment.javaClass.name)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }
}