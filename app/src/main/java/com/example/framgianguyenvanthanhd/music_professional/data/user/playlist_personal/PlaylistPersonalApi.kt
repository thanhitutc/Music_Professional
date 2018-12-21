package com.example.framgianguyenvanthanhd.music_professional.data.user.playlist_personal

import com.example.framgianguyenvanthanhd.music_professional.data.model.Playlist
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by admin on 12/22/2018.
 */
interface PlaylistPersonalApi {

    @POST("playlistpersonal/playlistdata.php")
    fun fetchPlaylists(@Body body: PlaylistPersonalRequest): Call<List<Playlist>>

    @POST("playlistpersonal/create.php")
    fun createPlaylist(@Body body: PlaylistPersonalRequest): Call<String>

    @POST("playlistpersonal/delete.php")
    fun deletePlaylist(@Body body: PlaylistPersonalRequest): Call<String>

    @POST("playlistpersonal/addsong.php")
    fun addSongToPlaylist(@Body body: PlaylistPersonalRequest): Call<String>

    @POST("playlistpersonal/deletesong.php")
    fun deleteSongFromPlaylist(@Body body: PlaylistPersonalRequest): Call<String>

}