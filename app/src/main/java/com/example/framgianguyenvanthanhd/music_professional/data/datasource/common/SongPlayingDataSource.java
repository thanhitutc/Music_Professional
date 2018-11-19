package com.example.framgianguyenvanthanhd.music_professional.data.datasource.common;

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;

import java.util.List;

/**
 * Created by admin on 11/18/2018.
 */

public interface SongPlayingDataSource {
    List<SongPlaying> getSongsPlaying();

    boolean insertSongPlaying(SongPlaying songPlaying);

    boolean deleteSongPlaying(SongPlaying songPlaying);
}
