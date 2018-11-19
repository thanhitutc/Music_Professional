package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddplaylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;

import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.EXTRA_ID_ALBUM_FOR_ADD_LIST_SONG;

/**
 * Created by MyPC on 02/02/2018.
 */

public class ListAddPlaylistOffActivity extends AppCompatActivity
        implements ContractListAddPlaylistOff.ListAddAlbumView {
    private static final int DEFAULT_ID_ALBUM = -1;
    private RecyclerView mRecyclerView;
    private ContractListAddPlaylistOff.ListAddAlbumPresenter mPresenter;
    private ListAddPlaylistOffAdapter mAdapter;

    public static Intent getInstance(Context context, int idAlbum) {
        Intent intent = new Intent(context, ListAddPlaylistOffActivity.class);
        intent.putExtra(EXTRA_ID_ALBUM_FOR_ADD_LIST_SONG, idAlbum);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song_add_favorite);
        initToolbar();
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void setPresenter(ContractListAddPlaylistOff.ListAddAlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onGetSongSuccess(List<SongOffline> songOfflines) {
        mAdapter = new ListAddPlaylistOffAdapter(songOfflines);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onGetSongFail() {
        Toast.makeText(this, R.string.announcement_add_song_album_success, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onInsertSongSuccessfully() {
        finish();
    }

    @Override
    public void onNoSongSelected() {
        Toast.makeText(this, R.string.announcement_add_favorite_nothing_song, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_song_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_ok:
                List<SongOffline> songOfflines = mAdapter.getSongOfflineSelected();
                if (songOfflines != null) {
                    int idAlbum = getIntent().getIntExtra(
                            EXTRA_ID_ALBUM_FOR_ADD_LIST_SONG,
                            DEFAULT_ID_ALBUM);
                    mPresenter.insertListSong(idAlbum, songOfflines);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_add_song_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        SongInPlaylistRepository songInPlaylistRepository = SongInPlaylistRepository.getInstance(this);
        mPresenter = new ListAddPlaylistOffPresenter(songInPlaylistRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadSongs();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_add_song_to_album));
    }
}
