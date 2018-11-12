package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SongDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongDeleted.TABLE_SONG_DELETED;


/**
 * Created by MyPC on 22/01/2018.
 */

public final class SongLocalDataSource extends DatabaseHelper implements SongDataSource {
    private static SongDataSource sSource;

    public static SongDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new SongLocalDataSource(context);
        }
        return sSource;
    }

    private SongLocalDataSource(Context context) {
        super(context);
    }

    @Override
    public List<SongOffline> getSong() {
        return getSongsFromMediaStore();
    }

    @Override
    public List<SongOffline> getSongByName(String name) {
        return null;
    }

    @Override
    public boolean deleteSong(SongOffline songOffline) {
        if (songOffline == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, songOffline.getId());
        long result = db.insert(TABLE_SONG_DELETED, null, contentValues);
        return result != -1;
    }
}
