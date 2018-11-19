package com.example.framgianguyenvanthanhd.music_professional.data.repository;

import android.content.Context;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.SongPlayingDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.SongPlayingLocalDataSource;

import java.util.List;

/**
 * Created by admin on 11/18/2018.
 */

public final class SongPlayingRepository implements SongPlayingDataSource{
    private static SongPlayingRepository sRepository;
    private SongPlayingDataSource mDataSource;

    public static SongPlayingRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new SongPlayingRepository(SongPlayingLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private SongPlayingRepository(SongPlayingDataSource instance) {
        mDataSource = instance;
    }

    @Override
    public List<SongPlaying> getSongsPlaying() {
        return mDataSource.getSongsPlaying();
    }

    @Override
    public boolean insertSongPlaying(SongPlaying songPlaying) {
        return mDataSource.insertSongPlaying(songPlaying);
    }

    @Override
    public boolean deleteSongPlaying(SongPlaying songPlaying) {
        return mDataSource.deleteSongPlaying(songPlaying);
    }
}
