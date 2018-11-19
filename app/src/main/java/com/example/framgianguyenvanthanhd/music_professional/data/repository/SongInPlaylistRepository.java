package com.example.framgianguyenvanthanhd.music_professional.data.repository;

import android.content.Context;


import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SongInPlaylistDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.SongInPlaylistLocalDataSource;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public final class SongInPlaylistRepository implements SongInPlaylistDataSource {
    private static SongInPlaylistRepository sRepository;
    private SongInPlaylistDataSource mSongInPlaylistDataSource;

    public static SongInPlaylistRepository getInstance(Context context) {
        if (sRepository == null) {
            sRepository =
                    new SongInPlaylistRepository(SongInPlaylistLocalDataSource.getInstance(context));
        }
        return sRepository;
    }

    private SongInPlaylistRepository(SongInPlaylistDataSource instance) {
        mSongInPlaylistDataSource = instance;
    }

    @Override
    public List<SongOffline> getAllSongForAdd() {
        return mSongInPlaylistDataSource.getAllSongForAdd();
    }

    @Override
    public List<SongOffline> getSongOfAlbum(int idAlbum) {
        return mSongInPlaylistDataSource.getSongOfAlbum(idAlbum);
    }

    @Override
    public boolean insertSongToAlbum(int idAlbum, String idSong) {
        return mSongInPlaylistDataSource.insertSongToAlbum(idAlbum, idSong);
    }

    @Override
    public void insertListSongToAlbum(int idAlbum, List<SongOffline> songOfflines, CallBackInsertAlbum callBack) {
        mSongInPlaylistDataSource.insertListSongToAlbum(idAlbum, songOfflines, callBack);
    }

    @Override
    public boolean deleteAllSongOfAlbum(int idAlbum) {
        return mSongInPlaylistDataSource.deleteAllSongOfAlbum(idAlbum);
    }

    @Override
    public boolean deleteSongInAlbum(String idSong, int idAlbum) {
        return mSongInPlaylistDataSource.deleteSongInAlbum(idSong, idAlbum);
    }
}
