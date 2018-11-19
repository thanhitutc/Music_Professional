package com.example.framgianguyenvanthanhd.music_professional.screens.offline.detailplaylistoff;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddplaylist.ListAddPlaylistOffActivity;

import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.EXTRA_ALBUM;

/**
 * Created by MyPC on 29/01/2018.
 */

public class DetailPlaylistOffActivity extends AppCompatActivity
        implements ContractDetailPlaylistOff.DetailAlbumView, OnClickSongDetailPlaylistOffListener {
    private static final int DEFAULT_ID_ALBUM = -1;
    private static final String DEFAULT_NAME_ALBUM = "Detail album";
    private ContractDetailPlaylistOff.DetailAlbumPresenter mPresenter;
    private RecyclerView mRecyclerViewDetail;
    private DetailPlaylistOffAdapter mAdapter;
    private int idAlbum;

    public static Intent getInstance(Context context, PlaylistOffline playlistOffline) {
        Intent intent = new Intent(context, DetailPlaylistOffActivity.class);
        intent.putExtra(EXTRA_ALBUM, (Parcelable) playlistOffline);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_album);
        initRecyclerView();
        initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadListSongDetail(idAlbum);
    }

    @Override
    public void setPresenter(ContractDetailPlaylistOff.DetailAlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadListDetailSong(List<SongOffline> songOfflines) {
        mAdapter = new DetailPlaylistOffAdapter(songOfflines);
        mRecyclerViewDetail.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void showLoadNoSong() {
        Toast.makeText(getBaseContext(), R.string.announcement_no_song_in_album, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showDeleteSongSuccess(SongOffline songOffline) {
        mAdapter.removeSong(songOffline);
    }

    @Override
    public void showDeleteSongFail() {
        Toast.makeText(getBaseContext(), R.string.announcement_delete_song_fail, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showAddSongFavoriteSuccess() {
        Toast.makeText(getBaseContext(), R.string.announcement_add_song_favorite_success,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddSongFavoriteFail() {
        Toast.makeText(getBaseContext(), R.string.announcement_add_song_favorite_fail,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickSongDetailAlbum(List<SongOffline> songOfflines, int position) {
//        startActivity(PlayMusicActivity.getInstance(this));
//        startService(MediaService.getInstance(this, songOfflines, position));
    }

    @Override
    public void onAddSongToFavorite(SongOffline songOffline) {
        mPresenter.addSongToFavorite(songOffline);
    }

    @Override
    public void onDeleteSong(SongOffline songOffline) {
        mPresenter.deleteSongOfAlbum(songOffline);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        findViewById(R.id.button_add_song_to_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ListAddPlaylistOffActivity.getInstance(getBaseContext(), idAlbum));
            }
        });
        mRecyclerViewDetail = findViewById(R.id.recycler_detail_album);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerViewDetail.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        idAlbum = DEFAULT_ID_ALBUM;
        String titleToolbar = DEFAULT_NAME_ALBUM;
        PlaylistOffline playlistOffline = getIntent().getParcelableExtra(EXTRA_ALBUM);
        idAlbum = playlistOffline.getId();
        titleToolbar = playlistOffline.getNameAlbum();
        initToolbar(titleToolbar);
        SongInPlaylistRepository songInPlaylistRepository =
                SongInPlaylistRepository.getInstance(getBaseContext());
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance();
        mPresenter = new DetailPlaylistOffPresenter(songInPlaylistRepository, favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    private void initToolbar(String titleToolbar) {
        Toolbar toolbar = findViewById(R.id.toolbar_detail_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(titleToolbar);
    }
}
