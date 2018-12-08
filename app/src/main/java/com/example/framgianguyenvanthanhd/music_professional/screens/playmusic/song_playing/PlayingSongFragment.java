package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.song_playing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository;
import com.example.framgianguyenvanthanhd.music_professional.helper.LinearLayoutManagerWithSmoothScroller;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.OnChangeSongListener;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.PlayMusicActivity;
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by admin on 11/19/2018.
 */

public class PlayingSongFragment extends Fragment implements PlayingSongAdapter.OnItemSongClickListener,
        OnChangeSongListener, ContractSongPlaying.SongPlayingView{

    private PlayingSongAdapter mSongAdapter;
    private RecyclerView mRecyclerView;
    private ContractSongPlaying.SongPlayingPresenter mPresenter;
    private PlayMusicActivity mPlayMusicActivity;
    private MediaService mService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playing_songs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPlayMusicActivity = (PlayMusicActivity) getActivity();
        mService = mPlayMusicActivity.getService();
        mRecyclerView = getView().findViewById(R.id.rc_song_playing);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity()));
//        layoutManager.startSmoothScroll(mSmoothScroller);
        mPresenter = new SongPlayingPresenter(SongPlayingRepository.getInstance(getActivity()));
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.getSongsPlaying();
        mPlayMusicActivity.setOnChangeSongListener(this);



    }

    @Override
    public void onItemSongClick(@NotNull SongPlaying song, @NotNull int position) {
       mPlayMusicActivity.playWithSong(song);
       mRecyclerView.smoothScrollToPosition(position);
       mSongAdapter.notifyDataSetChanged();
    }


    @Override
    public void onUpdateSong(SongPlaying songPlaying) {
        int position = mSongAdapter.getPositionSong(songPlaying);
        mRecyclerView.smoothScrollToPosition(position);
        mSongAdapter.showIconPlaying(position);
        mSongAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(ContractSongPlaying.SongPlayingPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void songsPlayingSuccess(List<SongPlaying> songPlayings) {
        mSongAdapter = new PlayingSongAdapter(songPlayings, this);
        mRecyclerView.setAdapter(mSongAdapter);
    }

    @Override
    public void songPlayingFail() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
