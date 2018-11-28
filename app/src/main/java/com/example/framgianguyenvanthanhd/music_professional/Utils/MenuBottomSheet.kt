package com.example.framgianguyenvanthanhd.music_professional.Utils

import android.graphics.drawable.Drawable
import com.example.framgianguyenvanthanhd.music_professional.App
import com.example.framgianguyenvanthanhd.music_professional.R

/**
 * Created by admin on 11/28/2018.
 */
enum class MenuBottomSheet(val id: Int, val title: String, val icon: Int) {
    ADD_PLAYING(1, App.getContext().getString(R.string.menu_add_playing), R.drawable.ic_song),
    ADD_FAVORITE(2, App.getContext().getString(R.string.menu_add_favorite), R.drawable.ic_favorite),
    ADD_PLAYLIST(3, App.getContext().getString(R.string.menu_add_playlist), R.drawable.ic_playlist)
}