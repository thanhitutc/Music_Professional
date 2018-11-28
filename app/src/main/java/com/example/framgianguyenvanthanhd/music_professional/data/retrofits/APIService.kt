package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterApi
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountApi

/**
 * Created by admin on 8/24/2018.
 */
class APIService {

    companion object {
        fun getSlideAPI(): DataService.SlideDataService? {
            return RetrofitClient.getClient(Constants.BASE_URL)?.create(DataService.SlideDataService::class.java)
        }

        fun getPlaylistAPI(): DataService.PlaylistDataService? {
            return RetrofitClient.getClient(Constants.BASE_URL)?.create(DataService.PlaylistDataService::class.java)
        }

        fun getTopicAPI(): DataService.TopicDataService? {
            return RetrofitClient.getClient(Constants.BASE_URL)?.create(DataService.TopicDataService::class.java)
        }

        fun getCategoryAPI(): DataService.CategoryDataService? {
            return RetrofitClient.getClient(Constants.BASE_URL)?.create(DataService.CategoryDataService::class.java)
        }

        fun getFavoriteAPI(): DataService.FavoriteDataService? {
            return RetrofitClient.getClient(Constants.BASE_URL)?.create(DataService.FavoriteDataService::class.java)
        }

        fun getPlayMostAPI(): DataService.PlayMostDataService? {
            return RetrofitClient.getClient(Constants.BASE_URL)?.create(DataService.PlayMostDataService::class.java)
        }

        fun getAccountApi(): AccountApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(AccountApi::class.java)
        }

        fun getSongParameter(): SongParameterApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(SongParameterApi::class.java)
        }
    }
}