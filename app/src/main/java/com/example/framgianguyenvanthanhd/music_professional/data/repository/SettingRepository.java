package com.example.framgianguyenvanthanhd.music_professional.data.repository;


import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SettingDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.Setting;

/**
 * Created by MyPC on 02/02/2018.
 */

public final class SettingRepository implements SettingDataSource {
    private static SettingRepository sRepository;
    private SettingDataSource mSettingDataSource;

    public static SettingRepository getInstance(SettingDataSource localDataSource) {
        if (sRepository == null) {
            sRepository = new SettingRepository(localDataSource);
        }
        return sRepository;
    }

    private SettingRepository(SettingDataSource instance) {
        mSettingDataSource = instance;
    }

    @Override
    public Setting getSetting() {
        return mSettingDataSource.getSetting();
    }

    @Override
    public void saveSetting(Setting setting) {
        mSettingDataSource.saveSetting(setting);
    }
}
