package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.ArrayList;
import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.DATABASE_NAME;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.DATABASE_VERSION;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseAlbum.SQL_CREATE_TABLE_ALBUM;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseAlbum.SQL_DELTE_ALBUM;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseFavorite.SQL_CREATE_TABLE_FAVORITE;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseFavorite.SQL_DELTE_FAVORITE;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabasePlaying.SQL_CREATE_TABLE_PLAYING;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabasePlaying.SQL_DELETE_PLAYING;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongDeleted.SQL_CREATE_TABLE_SONGDELETED;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongDeleted.SQL_DELTE_SONGDELETED;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongDeleted.TABLE_SONG_DELETED;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongInAlbum.SQL_CREATE_TABLE_SONG_IN_ALBUM;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabaseSongInAlbum.SQL_DELTE_SONG_IN_ALBUM;

/**
 * Database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ALBUM);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SONGDELETED);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SONG_IN_ALBUM);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PLAYING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELTE_ALBUM);
        sqLiteDatabase.execSQL(SQL_DELTE_FAVORITE);
        sqLiteDatabase.execSQL(SQL_DELTE_SONGDELETED);
        sqLiteDatabase.execSQL(SQL_DELTE_SONG_IN_ALBUM);
        sqLiteDatabase.execSQL(SQL_DELETE_PLAYING);
        onCreate(sqLiteDatabase);
    }

    public String[] getSongDeleted() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = { ID };
        Cursor cursor = db.query(TABLE_SONG_DELETED, project, null, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        String[] idDeletes = new String[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int i = 0;
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(ID));
                idDeletes[i] = id;
                cursor.moveToNext();
                i++;
            }
            cursor.close();
            db.close();
        }
        return idDeletes;
    }

    protected List<SongOffline> getSongsFromMediaStore() {
        String[] projection = {
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        Cursor cursor;
        String[] whereArgs = getSongDeleted();
        if (whereArgs.length != 0) {
            String whereClause = MediaStore.Audio.Media._ID + " NOT IN (";
            for (String arg : whereArgs) {
                whereClause += "?,";
            }
            whereClause = whereClause.substring(0, whereClause.length() - 1) + ")";
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, whereClause,
                            whereArgs, MediaStore.Audio.Media.DATE_ADDED + " DESC");
        } else {
            cursor = mContext.getContentResolver()
                    .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null,
                            MediaStore.Audio.Media.DATE_ADDED + " DESC");
        }
        if (cursor == null) {
            return null;
        }
        List<SongOffline> songOfflines = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                long duration =
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String singer =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                SongOffline songOffline = new SongOffline(id, title, singer, data, duration);
                songOfflines.add(songOffline);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return songOfflines;
    }
}
