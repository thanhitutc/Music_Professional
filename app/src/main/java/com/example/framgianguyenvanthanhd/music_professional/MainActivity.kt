package com.example.framgianguyenvanthanhd.music_professional

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.example.framgianguyenvanthanhd.music_professional.screens.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment: HomeFragment? = HomeFragment()
        val ft: FragmentTransaction? = supportFragmentManager.beginTransaction()
        ft?.add(R.id.main_containetr, homeFragment)?.commit()

    }
}
