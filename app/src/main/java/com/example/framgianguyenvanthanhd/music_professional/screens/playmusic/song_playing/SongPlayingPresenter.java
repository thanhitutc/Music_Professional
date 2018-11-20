package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.song_playing;

import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository;

import java.util.List;

/**
 * Created by admin on 11/20/2018.
 */

public class SongPlayingPresenter implements ContractSongPlaying.SongPlayingPresenter {
    private SongPlayingRepository mPlayingRepository;
    private ContractSongPlaying.SongPlayingView mView;

    public SongPlayingPresenter(SongPlayingRepository repository) {
        mPlayingRepository = repository;
    }

    @Override
    public void setView(ContractSongPlaying.SongPlayingView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        mView.setPresenter(this);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void getSongsPlaying() {
        List<SongPlaying> songPlayings = mPlayingRepository.getSongsPlaying();
        if (songPlayings != null) {
            mView.songsPlayingSuccess(songPlayings);
        } else {
            mView.songPlayingFail();
        }
    }
}
