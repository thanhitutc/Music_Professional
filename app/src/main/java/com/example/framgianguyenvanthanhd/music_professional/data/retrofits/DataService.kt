package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.data.model.*
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 30/08/2018.
 */
interface DataService {

    interface SlideDataService {
        @GET("Slide.php")
        fun getSlide(): Call<List<Slide>>?
    }

    interface PlaylistDataService {
        @GET("playlists.php")
        fun getPlaylistHome(): Call<List<Playlist>>?
    }

    interface TopicDataService {
        @GET("topic.php")
        fun getTopics(): Call<List<Topic>>?
    }

    interface CategoryDataService {
        @GET("category.php")
        fun getCategory(): Call<List<Category>>?
    }

    interface FavoriteDataService {
        @GET("favorites.php")
        fun getFavorite(): Call<List<SongHome>?>
    }

    interface PlayMostDataService {
        @GET("playstheweek.php")
        fun getPlayMost(): Call<List<SongHome>?>
    }
}