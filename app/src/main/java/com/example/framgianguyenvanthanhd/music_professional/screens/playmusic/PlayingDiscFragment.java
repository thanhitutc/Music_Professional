package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.framgianguyenvanthanhd.music_professional.MainActivity;
import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService;
import com.squareup.picasso.Picasso;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by admin on 11/19/2018.
 */

public class PlayingDiscFragment extends Fragment implements OnChangeSongListener, OnStatePlayingListener{

    private Animation mAnimation;
    private PlayMusicActivity mActivity;
    private ImageView mImageView;
    private MediaService mService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing_disc, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (PlayMusicActivity) getActivity();
        mImageView = getView().findViewById(R.id.image_playing);
        mActivity.setOnChangeSongListener(this);
        mActivity.setOnStatePlayingListener(this);
    }

    private void startAnimationImagePlaying() {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_image_playing);
        mAnimation.setRepeatMode(Animation.INFINITE);
        mImageView.startAnimation(mAnimation);
    }

    private void clearAnimationImagePlaying() {
        mImageView.clearAnimation();
    }

    @Override
    public void onUpdateSong(SongPlaying songPlaying) {
        if (!TextUtils.isEmpty(songPlaying.getImage())){
            Picasso.with(getContext()).load(songPlaying.getImage()).into(mImageView);
        } else {
            Picasso.with(getContext()).load(R.drawable.ic_song_playing).into(mImageView);
        }
    }

    @Override
    public void onSongPlaying() {
        startAnimationImagePlaying();
    }

    @Override
    public void onSongPause() {
        clearAnimationImagePlaying();
    }
}
