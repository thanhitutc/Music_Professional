package com.example.framgianguyenvanthanhd.music_professional.data.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by admin on 11/24/2018.
 */
interface AccountApi {

    @POST("register.php")
    fun register(@Body body: Account): Call<String>

    @POST("login.php")
    fun login(@Body body: Account): Call<Account>

    @POST("user_update.php")
    fun userUpdate(@Body body: Account): Call<Account>

    @POST("user_change_password.php")
    fun updatePassWord(@Body body: Account): Call<String>
}
