package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.data.model.Category
import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import com.example.framgianguyenvanthanhd.music_professional.data.model.Topic
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
}