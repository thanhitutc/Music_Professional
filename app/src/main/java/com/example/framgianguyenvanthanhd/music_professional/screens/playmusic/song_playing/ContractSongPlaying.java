package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.song_playing;

import com.example.framgianguyenvanthanhd.music_professional.BasePresenter;
import com.example.framgianguyenvanthanhd.music_professional.BaseView;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;

import java.util.List;

/**
 * Created by admin on 11/20/2018.
 */

public interface ContractSongPlaying {

    interface SongPlayingView extends BaseView<SongPlayingPresenter> {
        void songsPlayingSuccess(List<SongPlaying> songPlayings);

        void songPlayingFail();

    }

    interface SongPlayingPresenter extends BasePresenter<SongPlayingView> {
        void getSongsPlaying();
    }
}
