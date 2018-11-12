package com.example.framgianguyenvanthanhd.music_professional

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
                    replaceFragment(PersonalFragment(), "personal")
                    true
                }
                R.id.menu_cd -> {
                    val homeFragment = HomeFragment()
                    replaceFragment(homeFragment, "home_online")
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

    private fun replaceFragment(fragment: Fragment, tag: String) {
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

}
