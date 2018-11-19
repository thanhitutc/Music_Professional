package com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline;


import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface SongInPlaylistDataSource {

    List<SongOffline> getAllSongForAdd();

    List<SongOffline> getSongOfAlbum(int idAlbum);

    boolean insertSongToAlbum(int idAlbum, String idSong);

    void insertListSongToAlbum(int idAlbum, List<SongOffline> songOfflines, CallBackInsertAlbum callBack);

    boolean deleteAllSongOfAlbum(int idAlbum);

    boolean deleteSongInAlbum(String idSong, int idAlbum);

    /**
     * callback insert list to album
     **/
    interface CallBackInsertAlbum {
        void onComplete();

        void onInsertNoSong();
    }
}
