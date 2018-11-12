package com.example.framgianguyenvanthanhd.music_professional.data.repository;

import android.content.Context;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SongDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.SongLocalDataSource;

import java.util.List;

/**
 * Created by MyPC on 22/01/2018.
 */

public final class SongRepository implements SongDataSource {
    private static SongRepository sRepository;
    private SongDataSource mSongDataSource;

    public static SongRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new SongRepository(SongLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private SongRepository(SongDataSource instance) {
        mSongDataSource = instance;
    }

    @Override
    public List<SongOffline> getSong() {
        return mSongDataSource.getSong();
    }

    @Override

    public List<SongOffline> getSongByName(String name) {
        return mSongDataSource.getSongByName(name);
    }

    @Override
    public boolean deleteSong(SongOffline songOffline) {
        return mSongDataSource.deleteSong(songOffline);
    }
}
