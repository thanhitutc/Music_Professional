package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.data.comment.CommentApi
import com.example.framgianguyenvanthanhd.music_professional.data.download.DownloadApi
import com.example.framgianguyenvanthanhd.music_professional.data.search.SearchApi
import com.example.framgianguyenvanthanhd.music_professional.data.song_parameter.SongParameterApi
import com.example.framgianguyenvanthanhd.music_professional.data.user.AccountApi
import com.example.framgianguyenvanthanhd.music_professional.data.user.personal.PersonalApi
import com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal.PlaylistPersonalApi

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

        fun getComment(): CommentApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(CommentApi::class.java)
        }

        fun getPersonal(): PersonalApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(PersonalApi::class.java)
        }

        fun getSearch(): SearchApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(SearchApi::class.java)
        }

        fun getPlaylistPersonal(): PlaylistPersonalApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(PlaylistPersonalApi::class.java)
        }

        fun downloadSong(): DownloadApi {
            return RetrofitClient.getClient(Constants.BASE_URL).create(DownloadApi::class.java)
        }
    }
}
