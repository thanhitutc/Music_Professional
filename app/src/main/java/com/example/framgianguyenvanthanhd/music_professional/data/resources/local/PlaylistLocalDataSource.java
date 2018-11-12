package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.PlaylistOffDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

import java.util.ArrayList;
import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.TITLE;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseAlbum.TABLE_ALBUM;


/**
 * Created by MyPC on 25/01/2018.
 */

public final class PlaylistLocalDataSource extends DatabaseHelper implements PlaylistOffDataSource {
    private static PlaylistOffDataSource sSource;

    public static PlaylistOffDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new PlaylistLocalDataSource(context);
        }
        return sSource;
    }

    public PlaylistLocalDataSource(Context context) {
        super(context);
    }

    @Override
    public List<PlaylistOffline> getListAlbum() {
        return getAlbum();
    }

    @Override
    public boolean insertAlbum(String name) {
        if (name == null || name.equals("")) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TITLE, name);
            result = db.insert(TABLE_ALBUM, null, contentValues);
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean deleteAlbum(int idAlbum) {
        if (idAlbum == -1) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            result = db.delete(TABLE_ALBUM, ID + "=?", new String[]{String.valueOf(idAlbum)});
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean updateName(int idAlbum, String name) {
        if (name == null || idAlbum == -1) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            ContentValues values = new ContentValues();
            values.put(TITLE, name);
            result = db.update(TABLE_ALBUM, values, ID + "=?",
                    new String[]{String.valueOf(idAlbum)});
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public int getLastIdInsert() {
        return getLastInsertRow();
    }

    private int getLastInsertRow() {
        int id = -1;
        SQLiteDatabase db = getReadableDatabase();
        String[] project = {ID};
        String oderBy = ID + " DESC LIMIT 1";
        Cursor cursor = db.query(TABLE_ALBUM, project, null, null, null, null, oderBy);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(ID));
            cursor.close();
        }
        db.close();
        return id;
    }

    private List<PlaylistOffline> getAlbum() {
        List<PlaylistOffline> playlistOfflines = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = {ID, TITLE};
        Cursor cursor = db.query(TABLE_ALBUM, project, null, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                playlistOfflines.add(new PlaylistOffline(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        return playlistOfflines;
    }
}
