package com.example.framgianguyenvanthanhd.music_professional.screens.offline;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



/**
 * MainOfflinePagerAdapter
 * */
public class MainOfflinePagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public static final int COUNT_TAB = 3;

    public MainOfflinePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MainType.ALL_SONG:
                //return new AllSongFragment();

            case MainType.ALBUM:
                //return new AlbumFragment();

            case MainType.FAVORITE:
                //return new FavoriteFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT_TAB;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case MainType.ALL_SONG:
                return "Song";

            case MainType.ALBUM:
                return "Playlist";

            case MainType.FAVORITE:
                return "Favorite";

            default:
                return null;
        }
    }
}
