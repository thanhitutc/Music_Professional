package com.example.framgianguyenvanthanhd.music_professional.Utils

/**
 * Created by admin on 8/24/2018.
 */
class Constants {
    companion object {
        val BASE_URL : String = "https://thanhitutc.000webhostapp.com/Server/"

        val READ_TIME_OUT : Long = 10000
        val WRITE_TIME_OUT : Long = 10000
        val CONNECT_TIME_OUT : Long = 10000

        val DELAY_SLIDE : Long = 4000

        const val DEFAULT_SIZE_PLAYLIST_HOME = 3

        const val PLAYLIST_ACTION = "playlist_acction"
        const val PLAYLIST_EXTRA = "playlist_extra"

        /*
        * category
        * */
        const val CATEGORY_FROM_TOPIC_EXTRA = "category_from_topic_extra"
        const val CATEGORY_ID_EXTRA = "category_id_extra"

        /*
        * song home detail
        * */

        const val SONG_HOME_TYPE = "song_home_type"

    }
}