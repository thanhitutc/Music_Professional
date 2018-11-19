package com.example.framgianguyenvanthanhd.music_professional.data.resources.local;


import com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase;

/**
 * Created by MyPC on 23/01/2018.
 */

public class ContractSong {

    /**
     * Constant Database Favotite
     **/
    public static class DatabaseFavorite extends BaseColumsDatabase {
        public static final String TABLE_FAVORITE = "Favorite";
        public static final String IN_TABLE_FAVORITE = " IN ( ";
        public static final String NOT_IN_TABLE_FAVORITE = " NOT IN ( ";
        public static final String SQL_DELTE_FAVORITE = DROP_TABLE + TABLE_FAVORITE;
        public static final String SQL_CREATE_TABLE_FAVORITE =
                CREATE_TABLE + TABLE_FAVORITE + "(" + ID + " Text primary key)";
    }

    /**
     * Constant Database Playing
     **/
    public static class DatabasePlaying extends BaseColumsDatabase {
        public static final String TABLE_PLAYING = "Playing";
        public static final String IN_TABLE_PLAYING = " IN ( ";
        public static final String NOT_IN_TABLE_PLAYING = " NOT IN ( ";
        public static final String SQL_DELETE_PLAYING = DROP_TABLE + TABLE_PLAYING;
        public static final String SQL_CREATE_TABLE_PLAYING =
                CREATE_TABLE + TABLE_PLAYING + "(" + ID + " Text primary key,"
                        + TITLE + " TEXT,"
                        + SINGER + " TEXT,"
                        + IMAGE + " TEXT,"
                        + RESOURCE + " TEXT,"
                        + MODE +  INTEGER;
    }

    /**
     * Constant Database SongOffline deteted
     */
    public static class DatabaseSongDeleted extends BaseColumsDatabase {
        public static final String TABLE_SONG_DELETED = "SongDeleted";
        public static final String SQL_DELTE_SONGDELETED = DROP_TABLE + TABLE_SONG_DELETED;
        public static final String SQL_CREATE_TABLE_SONGDELETED =
                CREATE_TABLE + TABLE_SONG_DELETED + "(" + ID + " TEXT primary key)";
    }

    /**
     * Constant Database PlaylistOffline
     **/
    public static class DatabaseAlbum extends BaseColumsDatabase {
        public static final String TABLE_ALBUM = "PlaylistOffline";

        public static final String SQL_DELTE_ALBUM = DROP_TABLE + TABLE_ALBUM;

        public static final String SQL_CREATE_TABLE_ALBUM = CREATE_TABLE
                + TABLE_ALBUM
                + "("
                + ID
                + " INTEGER primary key AUTOINCREMENT,"
                + TITLE
                + " TEXT)";
    }

    /**
     * Constant SongOffline in PlaylistOffline
     */
    public static class DatabaseSongInAlbum extends BaseColumsDatabase {
        public static final String ID_SONG_IN_ALBUM = "ID_SONG_ALBUM";
        public static final String ID_ALBUM_OF_SONG = "ID_ALBUM_OF_SONG";
        public static final String TABLE_SONG_ALBUM = "SongAlbum";
        public static final String SQL_DELTE_SONG_IN_ALBUM = DROP_TABLE + TABLE_SONG_ALBUM;
        public static final String SQL_CREATE_TABLE_SONG_IN_ALBUM = CREATE_TABLE
                + TABLE_SONG_ALBUM
                + "("
                + ID_SONG_IN_ALBUM
                + " INTEGER primary key AUTOINCREMENT,"
                + ID
                + TEXT
                + ID_ALBUM_OF_SONG
                + INTEGER;
    }
}
