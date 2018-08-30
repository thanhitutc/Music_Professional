package com.example.framgianguyenvanthanhd.music_professional

/**
 * Created by admin on 8/30/2018.
 */
interface BasePresenter<T> {
    fun setView(view: T)

    fun onStart()

    fun onStop()
}