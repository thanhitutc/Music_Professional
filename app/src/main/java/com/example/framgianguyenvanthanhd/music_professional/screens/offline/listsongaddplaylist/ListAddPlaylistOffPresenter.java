package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddplaylist;



import com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline.SongInPlaylistDataSource;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;

import java.util.List;

/**
 * Created by MyPC on 02/02/2018.
 */

public class ListAddPlaylistOffPresenter implements ContractListAddPlaylistOff.ListAddAlbumPresenter {
    private SongInPlaylistRepository mSongInPlaylistRepository;
    private ContractListAddPlaylistOff.ListAddAlbumView mView;

    public ListAddPlaylistOffPresenter(SongInPlaylistRepository songInPlaylistRepository) {
        mSongInPlaylistRepository = songInPlaylistRepository;
    }

    @Override
    public void setView(ContractListAddPlaylistOff.ListAddAlbumView view) {
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
    public void loadSongs() {
        List<SongOffline> songOfflines = mSongInPlaylistRepository.getAllSongForAdd();
        if (songOfflines != null) {
            mView.onGetSongSuccess(songOfflines);
        } else {
            mView.onGetSongFail();
        }
    }

    @Override
    public void insertListSong(int idAlbum, List<SongOffline> songOfflines) {
        mSongInPlaylistRepository.insertListSongToAlbum(idAlbum, songOfflines,
                new SongInPlaylistDataSource.CallBackInsertAlbum() {
                    @Override
                    public void onComplete() {
                        mView.onInsertSongSuccessfully();
                    }

                    @Override
                    public void onInsertNoSong() {
                        mView.onNoSongSelected();
                    }
                });
    }
}
