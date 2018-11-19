package com.example.framgianguyenvanthanhd.music_professional.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.DATA;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.DURATION;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.ID;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.SINGER;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.BaseColumsDatabase.TITLE;


/**
 * Model song
 */
public class SongOffline implements Parcelable {
    private String mId;
    private String mTitle;
    private String mSinger;
    private String mData;
    private long mDuration;

    public SongOffline(String id, String title, String singer, String data, long duration) {
        mId = id;
        mTitle = title;
        mSinger = singer;
        mData = data;
        mDuration = duration;
    }

    public SongOffline(Cursor cursor) {
        mId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        mTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        mDuration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        mData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        mSinger = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
    }

    public SongOffline(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mSinger = in.readString();
        mData = in.readString();
        mDuration = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mSinger);
        parcel.writeString(mData);
        parcel.writeLong(mDuration);
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSinger() {
        return mSinger;
    }

    public String getData() {
        return mData;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSinger(String singer) {
        mSinger = singer;
    }

    public void setData(String data) {
        mData = data;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        if (mId != null) {
            contentValues.put(ID, mId);
        }
        if (mTitle != null) {
            contentValues.put(TITLE, mTitle);
        }
        if (mSinger != null) {
            contentValues.put(SINGER, mSinger);
        }
        if (mDuration != 0) {
            contentValues.put(DURATION, mDuration);
        }
        if (mData != null) {
            contentValues.put(DATA, mData);
        }
        return contentValues;
    }

    public static final Creator<SongOffline> CREATOR = new Creator<SongOffline>() {
        public SongOffline createFromParcel(Parcel in) {
            return new SongOffline(in);
        }

        public SongOffline[] newArray(int size) {
            return new SongOffline[size];
        }
    };
}
