package com.example.framgianguyenvanthanhd.music_professional.screens.offline.addsongtoplaylist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistOfflineRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;

import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.ACTION_ID_SONG_ADD_TO_ALBUM;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.EXTRA_ID_SONG_ADD_TO_ALBUM;

/**
 * Created by MyPC on 26/01/2018.
 */

public class SongAddToPlaylistOffActivity extends AppCompatActivity
        implements ContractSongAddToPlaylist.SongAddToAlbumView, View.OnClickListener,
        OnClickPlaylistOffListener {
    public static final String DEFAULT_ID_SONG_ADD = "-1";
    public static final int SPAN_COUNT = 2;
    private RecyclerView mRecyclerAlbum;
    private ContractSongAddToPlaylist.SongAddToAlbumPresenter mPresenter;
    private FloatingActionButton mButtonAddAlbum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add_playlist);
        initToolbar();
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setPresenter(ContractSongAddToPlaylist.SongAddToAlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListAlbum(List<PlaylistOffline> playlistOfflines) {
        ListPlaylistAddSongAdapter mAdapter = new ListPlaylistAddSongAdapter(playlistOfflines);
        mRecyclerAlbum.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void showNoListAlbum() {
        showToast(getBaseContext().getResources().getString(R.string.announcement_no_album));
    }

    @Override
    public void showAddSongSuccess() {
        showToast(getBaseContext().getResources()
                .getString(R.string.announcement_add_song_album_success));
        finish();
    }

    @Override
    public void showAddSongFail() {
        showToast(getBaseContext().getResources()
                .getString(R.string.announcement_add_song_album_fail));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_album:
                createDialogAddAlbum();
                break;
        }
    }

    @Override
    public void onItemClickSong(PlaylistOffline playlistOffline) {
        mPresenter.addSongToAlbum(playlistOffline.getId());
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
        mButtonAddAlbum = findViewById(R.id.button_add_album);
        mButtonAddAlbum.setOnClickListener(this);
        mRecyclerAlbum = findViewById(R.id.recycler_list_album);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mRecyclerAlbum.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        String idSongAdd = DEFAULT_ID_SONG_ADD;
        if (getIntent().getAction().equals(ACTION_ID_SONG_ADD_TO_ALBUM)) {
            idSongAdd =
                    getIntent().getStringExtra(EXTRA_ID_SONG_ADD_TO_ALBUM);
        }
        PlaylistOfflineRepository playlistOfflineRepository = PlaylistOfflineRepository.getInstance(this);
        SongInPlaylistRepository songInPlaylistRepository = SongInPlaylistRepository.getInstance(this);
        mPresenter = new SongAddToPlaylistPresenter(playlistOfflineRepository, songInPlaylistRepository, idSongAdd);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadListSong();
    }

    private void createDialogAddAlbum() {
        LayoutInflater inflater = this.getLayoutInflater();
        final View al = inflater.inflate(R.layout.layout_dialog_add_playlist, null);
        final EditText editTextNewAlbum = al.findViewById(R.id.edit_text_new_album);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getBaseContext().getResources().getString(R.string.title_dialog_add_album));
        dialog.setView(al);
        dialog.setNegativeButton(getBaseContext().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.addNewAlbum(editTextNewAlbum.getText().toString());
                    }
                });

        dialog.setPositiveButton(getBaseContext().getResources().getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }

    private void showToast(String content) {
        Toast.makeText(getBaseContext(), content, Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_album);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_add_song_to_album));
    }
}
