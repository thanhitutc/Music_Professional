package com.example.framgianguyenvanthanhd.music_professional.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.framgianguyenvanthanhd.music_professional.App;

/**
 * Created by MyPC on 31/01/2018.
 */

public final class SharedPrefs {
    private static final String PREFS_NAME = "com.framgia.music5.share_prefs";
    private static SharedPrefs mInstance;
    private SharedPreferences mSharedPreferences;

    private SharedPrefs() {
        mSharedPreferences = App.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPrefs();
        }
        return mInstance;
    }

    public void updateLastPlay(String id, String title, String image, String singer, String resource) {
        put(KeysPref.ID_SONG_PLAYING.name(), id);
        put(KeysPref.NAME_PLAYING.name(), title);
        put(KeysPref.IMAGE_PLAYING.name(), image);
        put(KeysPref.SINGER_PLAYING.name(), singer);
        put(KeysPref.RESOURCE_PLAYING.name(), resource);
    }

    public void cleanUserInfo(){
        put(KeysPref.ID_ACCOUNT.name(), "");
        put(KeysPref.USER_NAME.name(), "");
        put(KeysPref.PASS_USER.name(), "");
        put(KeysPref.LOGIN_TYPE.name(), "");
        put(KeysPref.FIRST_NAME.name(), "");
        put(KeysPref.LAST_NAME.name(), "");
        put(KeysPref.AVATAR.name(), "");
    }

    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        }
        return null;
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
