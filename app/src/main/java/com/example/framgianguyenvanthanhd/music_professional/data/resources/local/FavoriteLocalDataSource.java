package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;


import com.example.framgianguyenvanthanhd.music_professional.data.datasource.FavoriteDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;


/**
 * Created by MyPC on 24/01/2018.
 */

public final class FavoriteLocalDataSource extends DatabaseHelper implements FavoriteDataSource {
    public static final String DESC = " DESC";
    private static FavoriteDataSource sSource;
    private Context mContext;

    public static FavoriteDataSource getInstance(Context context) {
        if (sSource == null) {
            sSource = new FavoriteLocalDataSource(context);
        }
        return sSource;
    }

    private FavoriteLocalDataSource(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<SongOffline> getSongFavorite(int type) {
        switch (type) {
            case FavoriteType.IN_TABLE_FAVORITE:
                return getSongFavFromMedia(ContractSong.DatabaseFavorite.IN_TABLE_FAVORITE);
            case FavoriteType.NOT_IN_TABLE_FAVORITE:
                return getSongFavFromMedia(ContractSong.DatabaseFavorite.NOT_IN_TABLE_FAVORITE);
            default:
                return null;
        }
    }

    @Override
    public boolean deleteFavorite(String idSong) {
        if (idSong == null) {
            return false;
        }
        long result;
        SQLiteDatabase db = getWritableDatabase();
        try {
            result = db.delete(ContractSong.DatabaseFavorite.TABLE_FAVORITE,
                    ID + "=?", new String[] { idSong });
        } finally {
            db.close();
        }
        return result != -1;
    }

    @Override
    public boolean insertSongToFavorite(String idSong) {
        if (idSong == null) {
            return false;
        }
        long result;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID, idSong);
            result = db.insert(ContractSong.DatabaseFavorite.TABLE_FAVORITE, null, contentValues);
        } finally {
            db.close();
        }

        return result != -1;
    }

    @Override
    public void insertListSongToFavorite(List<? extends SongOffline> songOfflines, @NotNull CallBackInsertFavorite callBack) {
        if (songOfflines == null) {
            callBack.onInsertNoSong();
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < songOfflines.size(); i++) {
                values.put(ID, songOfflines.get(i).getId());
                long result =
                        db.insertWithOnConflict(ContractSong.DatabaseFavorite.TABLE_FAVORITE, null,
                                values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            callBack.onComplete();
        } finally {
            db.close();
        }
    }


    private String[] getDataSongFavorite() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] project = { ID };
        Cursor cursor =
                db.query(ContractSong.DatabaseFavorite.TABLE_FAVORITE, project, null, null, null,
                        null, null);
        if (cursor == null) {
            return null;
        }
        String[] idFavorite = new String[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int i = 0;
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(ID));
                idFavorite[i] = id;
                cursor.moveToNext();
                i++;
            }
            cursor.close();
            db.close();
        }
        return idFavorite;
    }

    private String[] getFavoriteApplication() {
        ArrayList<String> whereFav = new ArrayList<>(Arrays.asList(getDataSongFavorite()));
        ArrayList<String> whereDelete = new ArrayList<>(Arrays.asList(getSongDeleted()));
        whereFav.removeAll(whereDelete);
        return whereFav.toArray(new String[0]);
    }

    private List<SongOffline> getSongFavFromMedia(String where) {
        String[] whereArg = getFavoriteApplication();
        Cursor cursor;
        if (where.equals(ContractSong.DatabaseFavorite.NOT_IN_TABLE_FAVORITE)
                && whereArg.length == 0) {
            return getSongsFromMediaStore();
        }
        if (whereArg.length != 0) {
            String whereClause = MediaStore.Audio.Media._ID + where;
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

    @Override
    public void fetchFavoriteHome(@NotNull OnResponseFavoriteHome onResponse) {
        // todo not implement
    }
}
