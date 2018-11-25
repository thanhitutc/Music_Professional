package com.example.framgianguyenvanthanhd.music_professional.data.retrofits

import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 8/24/2018.
 */
class RetrofitClient {
    companion object  {
        private lateinit var sRetrofit : Retrofit

        @JvmStatic
        fun getClient(baseUrl : String) : Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            var okHttpClient : OkHttpClient = OkHttpClient().newBuilder()
                    .addInterceptor(interceptor)
                    .readTimeout(Constants.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(Constants.WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .build()

            var gson : Gson = GsonBuilder().setLenient().create()

            sRetrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return sRetrofit
        }
    }
}