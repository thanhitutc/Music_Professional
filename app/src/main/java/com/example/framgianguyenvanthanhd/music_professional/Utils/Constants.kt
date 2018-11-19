package com.example.framgianguyenvanthanhd.music_professional.Utils

import android.Manifest

/**
 * Created by admin on 8/24/2018.
 */
class Constants {
    companion object {
        val BASE_URL: String = "https://thanhitutc.000webhostapp.com/Server/"

        val READ_TIME_OUT: Long = 10000
        val WRITE_TIME_OUT: Long = 10000
        val CONNECT_TIME_OUT: Long = 10000

        val DELAY_SLIDE: Long = 4000

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

    /**
     * Constant Intent
     */
    object ConstantIntent {
        const val EXTRA_ID_SONG_ADD_TO_ALBUM = "com.framgia.music5.extra_id_song_to_album"
        const val ACTION_ID_SONG_ADD_TO_ALBUM = "com.framgia.music5.action_id_song_to_album"
        const val EXTRA_ALBUM = "com.framgia.music5.extra_album"
        const val EXTRA_NAME_ALBUM_DETAIL = "com.framgia.music5.extra_name_album_detail"
        const val ACTION_ID_ALBUM_DETAIL = "com.framgia.music5.action_id_album_detail"
        const val ACTION_INIT_SONG_SERVICE = "com.framgia.music5.action_init_song_service"
        const val EXTRA_INIT_SONG_SERVICE = "com.framgia.music5.extra_init_song_service"
        const val EXTRA_INIT_POSITION_SONG_SERVICE = "com.framgia.music5.extra_init_position_song_service"
        const val EXTRA_ID_ALBUM_FOR_ADD_LIST_SONG = "com.framgia.music5.extra_id_album_for_add_list_song"
        const val MAIN_TYPE = "main_type"
    }

    /**
     * Constant broadcast
     */
    object ConstantBroadcast {
        const val ACTION_STATE_MEDIA = "com.framgia.music5.action_state_media"
        const val EXTRA_STATE_MEDIA = "com.framgia.music5.extra_state_media"
        const val ACTION_UPDATE_FAVORITE = "com.framgia.music5.action_update_favorite"
        const val EXTRA_SONG_UPDATE_FAVORITE = "com.framgia.music5.extra_song_update_favorite"
    }

    /**
     * Constant Shared Preference
     */

    object ConstantSharePrefs {
        const val PREF_SHUFFLE_MEDIA = "com.framgia.music5.pref_shuffle_media"
        const val PREF_REPEAT_MEDIA = "com.framgia.music5.pref_repeat_media"
    }

    /**
     * Constant permission
     */
    object ConstantPermission {
        const val REQUEST_READ_STORAGE = 199

    }
}