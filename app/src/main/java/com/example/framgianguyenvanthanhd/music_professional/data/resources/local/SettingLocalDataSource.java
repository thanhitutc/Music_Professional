package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.Context;

import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs;
import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SettingDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.Setting;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantSharePrefs.PREF_REPEAT_MEDIA;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantSharePrefs.PREF_SHUFFLE_MEDIA;


/**
 * Created by MyPC on 02/02/2018.
 */

public final class SettingLocalDataSource implements SettingDataSource {
    private static SettingDataSource sSource;
    private SharedPrefs mSharedPrefs;

    public static SettingDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new SettingLocalDataSource(context);
        }
        return sSource;
    }

    private SettingLocalDataSource(Context context) {
        mSharedPrefs = SharedPrefs.getInstance();
    }

    @Override
    public Setting getSetting() {
        Setting setting = new Setting();
        setting.setShuffleMode(mSharedPrefs.get(PREF_SHUFFLE_MEDIA, Boolean.class));
        setting.setRepeatMode(mSharedPrefs.get(PREF_REPEAT_MEDIA, Integer.class));
        return setting;
    }

    @Override
    public void saveSetting(Setting setting) {
        if (setting == null) {
            return;
        }
        mSharedPrefs.put(PREF_REPEAT_MEDIA, setting.getRepeatMode());
        mSharedPrefs.put(PREF_SHUFFLE_MEDIA, setting.isShuffleMode());
    }
}
