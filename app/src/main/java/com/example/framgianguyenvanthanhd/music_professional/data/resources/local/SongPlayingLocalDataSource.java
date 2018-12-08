package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.framgianguyenvanthanhd.music_professional.data.datasource.common.SongPlayingDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;

import java.util.ArrayList;
import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.IMAGE;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.RESOURCE;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.SINGER;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.TITLE;
import static com.example.framgianguyenvanthanhd.music_professional.data.resources.local.ContractSong.DatabasePlaying.TABLE_PLAYING;

/**
 * Created by admin on 11/18/2018.
 */

public class SongPlayingLocalDataSource extends DatabaseHelper implements SongPlayingDataSource {
    private static SongPlayingDataSource sSource;
    private Context mContext;
    private static final String DESC = " DESC";

    public static SongPlayingDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new SongPlayingLocalDataSource(context);
        }
        return sSource;
    }

    private SongPlayingLocalDataSource(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<SongPlaying> getSongsPlaying() {
        List<SongPlaying> playings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = {ID, TITLE, SINGER, IMAGE, RESOURCE};
        Cursor cursor = db.query(TABLE_PLAYING, project, null, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(ID));
                String title = cursor.getString(cursor.getColumnIndex(TITLE));
                String singer = cursor.getString(cursor.getColumnIndex(SINGER));
                String image = cursor.getString(cursor.getColumnIndex(IMAGE));
                String resource = cursor.getString(cursor.getColumnIndex(RESOURCE));
                playings.add(new SongPlaying(id, title, singer, image, resource));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        return playings;
    }

    @Override
    public boolean insertSongPlaying(SongPlaying songPlaying) {
        long result;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, songPlaying.getId());
            contentValues.put(TITLE, songPlaying.getName());
            contentValues.put(SINGER, songPlaying.getSinger());
            contentValues.put(IMAGE, songPlaying.getImage());
            contentValues.put(RESOURCE, songPlaying.getResource());
            result = db.insert(TABLE_PLAYING, null, contentValues);
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean deleteSongPlaying(SongPlaying songPlaying) {
        long result;
        SQLiteDatabase db = getWritableDatabase();
        try {
            result = db.delete(TABLE_PLAYING,
                    ID + "=?", new String[]{songPlaying.getId()});
        } finally {
            db.close();
        }
        return result != -1;
    }

}
