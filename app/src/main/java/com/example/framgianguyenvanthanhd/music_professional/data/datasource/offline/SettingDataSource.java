package com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline;


import com.example.framgianguyenvanthanhd.music_professional.data.model.Setting;

/**
 * Created by MyPC on 02/02/2018.
 */

public interface SettingDataSource {

    Setting getSetting();

    void saveSetting(Setting setting);
}
