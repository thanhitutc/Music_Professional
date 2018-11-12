package com.example.framgianguyenvanthanhd.music_professional.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.TITLE;

/***
 * PlaylistOffline
 * */
public class PlaylistOffline implements Parcelable {
    private int mId;
    private String mNameAlbum;

    public PlaylistOffline(int id, String name) {
        mId = id;
        mNameAlbum = name;
    }

    public PlaylistOffline(Cursor cursor) {
        mId = cursor.getInt(cursor.getColumnIndex(ID));
        mNameAlbum = cursor.getString(cursor.getColumnIndex(TITLE));
    }

    public PlaylistOffline(Parcel in) {
        mId = in.readInt();
        mNameAlbum = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mNameAlbum);
    }

    public int getId() {
        return mId;
    }

    public String getNameAlbum() {
        return mNameAlbum;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setNameAlbum(String nameAlbum) {
        mNameAlbum = nameAlbum;
    }

    public static final Creator<PlaylistOffline> CREATOR = new Creator<PlaylistOffline>() {
        public PlaylistOffline createFromParcel(Parcel in) {
            return new PlaylistOffline(in);
        }

        public PlaylistOffline[] newArray(int size) {
            return new PlaylistOffline[size];
        }
    };
}
