package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 11/19/2018.
 */

public class PlayingPagerAdapter extends FragmentPagerAdapter {

    private static final int COUNT_TAB = 2;

    public PlayingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PlayingType.LIST:
                return new PlayingSongFragment();

            case PlayingType.DISC:
                return new PlayingDiscFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT_TAB;
    }
}
