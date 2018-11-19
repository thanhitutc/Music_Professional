package com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline;


import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 22/01/2018.
 */

public interface SongDataSource {

    List<SongOffline> getSong();

    List<SongOffline> getSongByName(String name);

    boolean deleteSong(SongOffline songOffline);
}
