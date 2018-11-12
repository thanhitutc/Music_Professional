package com.example.framgianguyenvanthanhd.music_professional.data.repository;

import android.content.Context;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.PlaylistOffDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.PlaylistLocalDataSource;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public final class PlaylistOfflineRepository implements PlaylistOffDataSource {
    private static PlaylistOfflineRepository sRepository;
    private PlaylistOffDataSource mPlaylistDataSource;

    public static PlaylistOfflineRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new PlaylistOfflineRepository(PlaylistLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private PlaylistOfflineRepository(PlaylistOffDataSource instance) {
        mPlaylistDataSource = instance;
    }

    @Override
    public List<PlaylistOffline> getListAlbum() {
        return mPlaylistDataSource.getListAlbum();
    }

    @Override
    public boolean insertAlbum(String name) {
        return mPlaylistDataSource.insertAlbum(name);
    }

    @Override
    public boolean deleteAlbum(int idAlbum) {
        return mPlaylistDataSource.deleteAlbum(idAlbum);
    }

    @Override
    public boolean updateName(int idAlbum, String name) {
        return mPlaylistDataSource.updateName(idAlbum, name);
    }

    @Override
    public int getLastIdInsert() {
        return mPlaylistDataSource.getLastIdInsert();
    }
}
