package com.example.framgianguyenvanthanhd.music_professional

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.framgianguyenvanthanhd.music_professional.screens.home.HomeFragment
import com.example.framgianguyenvanthanhd.music_professional.screens.personal.PersonalFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateBottomNavigation()
    }

    private fun initiateBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_account -> {
                    initFragment(PersonalFragment())
                    true
                }
                R.id.menu_cd -> {
                    val homeFragment = HomeFragment()
                    initFragment(homeFragment)
                    true
                }
                R.id.menu_more -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
        bottom_navigation.selectedItemId = R.id.menu_account
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val frag = supportFragmentManager.findFragmentByTag(tag)
        if (frag != null && frag.isVisible) return

        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount>0) {
            fragmentManager.popBackStack()
        }
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.main_containetr, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun initFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.main_containetr, fragment)
        ft.commit()
    }

    fun setDefaultPersonalTab() {
        bottom_navigation.selectedItemId = R.id.menu_account
    }

    fun isDisplayBottomNavigation(isDisplay : Boolean) {
        bottom_navigation.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    fun isDisplayToolbar(isDisplay: Boolean) {
        toolbar_main.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

}
