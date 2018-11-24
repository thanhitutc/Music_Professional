package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framgianguyenvanthanhd.music_professional.R;

/**
 * Created by admin on 11/19/2018.
 */

public class PlayingDiscFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing_disc, container, false);
    }
}
