package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SongInPlaylistDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongInAlbum.ID_ALBUM_OF_SONG;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongInAlbum.TABLE_SONG_ALBUM;


/**
 * Created by MyPC on 25/01/2018.
 */

public final class SongInPlaylistLocalDataSource extends DatabaseHelper
        implements SongInPlaylistDataSource {
    public static final String DESC = " DESC";
    public static final String WHERE = " IN ( ";
    private static SongInPlaylistDataSource sSource;
    private Context mContext;

    public static SongInPlaylistDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new SongInPlaylistLocalDataSource(context);
        }
        return sSource;
    }

    public SongInPlaylistLocalDataSource(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<SongOffline> getAllSongForAdd() {
        return getSongsFromMediaStore();
    }

    @Override
    public List<SongOffline> getSongOfAlbum(int idAlbum) {
        return getSongInAlbumFromMedia(idAlbum);
    }

    @Override
    public boolean insertSongToAlbum(int idAlbum, String idSong) {
        if (idAlbum == -1 || idSong == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            ContentValues values = new ContentValues();
            values.put(ID_ALBUM_OF_SONG, idAlbum);
            values.put(ID, idSong);
            result = db.insert(TABLE_SONG_ALBUM, null, values);
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public void insertListSongToAlbum(int idAlbum, List<SongOffline> songOfflines, CallBackInsertAlbum callBack) {
        if (songOfflines == null) {
            callBack.onInsertNoSong();
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < songOfflines.size(); i++) {
                values.put(ID, songOfflines.get(i).getId());
                values.put(ID_ALBUM_OF_SONG, idAlbum);
                long result =
                        db.insertWithOnConflict(TABLE_SONG_ALBUM,
                                null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            callBack.onComplete();
        } finally {
            db.close();
        }
    }

    @Override
    public boolean deleteAllSongOfAlbum(int idAlbum) {
        if (idAlbum == -1) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        try {
            result = db.delete(TABLE_SONG_ALBUM, ID_ALBUM_OF_SONG + "=?", new String[]{
                    String.valueOf(idAlbum)
            });
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean deleteSongInAlbum(String idSong, int idAlbum) {
        if (idAlbum == -1 || idSong == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        long result;
        String whereClause = ID_ALBUM_OF_SONG + "=? And " + ID + "=?";
        try {
            result = db.delete(TABLE_SONG_ALBUM, whereClause, new String[]{
                    String.valueOf(idAlbum), idSong
            });
        } finally {
            db.close();
        }
        return result != -1;
    }

    private ArrayList<String> getIdSongInAlbum(int idAlbum) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = {ID};
        String whereClause = ID_ALBUM_OF_SONG + "=?";
        String[] whereArgs = {String.valueOf(idAlbum)};
        Cursor cursor =
                db.query(TABLE_SONG_ALBUM, project, whereClause, whereArgs, null, null, null);
        if (cursor == null) {
            return null;
        }
        ArrayList<String> idSong = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(ID));
                idSong.add(id);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        return idSong;
    }

    private String[] getSongInAlbumApplication(int idAlbum) {
        ArrayList<String> whereSongInAlbum = getIdSongInAlbum(idAlbum);
        ArrayList<String> whereSongDeleted = new ArrayList<>(Arrays.asList(getSongDeleted()));
        whereSongInAlbum.removeAll(whereSongDeleted);
        return whereSongInAlbum.toArray(new String[0]);
    }

    private List<SongOffline> getSongInAlbumFromMedia(int idAlbum) {
        String[] whereArg = getSongInAlbumApplication(idAlbum);
        Cursor cursor;
        if (whereArg.length != 0) {
            String whereClause = MediaStore.Audio.Media._ID + WHERE;
            for (String arg : whereArg) {
                whereClause += "?,";
            }
            whereClause = whereClause.substring(0, whereClause.length() - 1) + ")";
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, whereClause, whereArg,
                            MediaStore.Audio.Media.DATE_ADDED + DESC);
        } else {
            return null;
        }
        if (cursor == null) {
            return null;
        }
        List<SongOffline> songOfflines = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                songOfflines.add(new SongOffline(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return songOfflines;
    }
}
