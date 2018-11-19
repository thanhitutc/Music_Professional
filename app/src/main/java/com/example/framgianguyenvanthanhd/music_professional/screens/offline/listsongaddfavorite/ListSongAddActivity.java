package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddfavorite;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;

import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantPermission.REQUEST_READ_STORAGE;
import static com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong.AllSongFragment.PERMISSION_READ_EXTERNAL;

/**
 * Created by MyPC on 01/02/2018.
 */

public class ListSongAddActivity extends AppCompatActivity
        implements ContractListSongAdd.ListSongAddView {
    private RecyclerView mRecyclerView;
    SongAddToFavoriteAdapter mAdapter;
    private ContractListSongAdd.ListSongAddPresenter mPresenter;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, ListSongAddActivity.class);
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
    public void setPresenter(ContractListSongAdd.ListSongAddPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListSongAdd(List<SongOffline> songOfflines) {
        mAdapter = new SongAddToFavoriteAdapter(songOfflines);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showNoListSongAdd() {
        Toast.makeText(this, R.string.announcement_list_favorite_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddSuccess() {
        finish();
        Toast.makeText(this, R.string.announcement_add_song_favorite_success, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showNoSongAdd() {
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
                List<SongOffline> songOfflines = mAdapter.getSongOfflineCheck();
                if (songOfflines != null) {
                    mPresenter.addSongToFavorite(songOfflines);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_add_song_to_favorite));
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_add_song_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance();
        mPresenter = new ListSongAddPresenter(favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        if (checkPermission()) {
            mPresenter.loadListSongAdd();
        }
    }

    private boolean checkPermission() {
        boolean isAllow = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            isAllow = false;
            ActivityCompat.requestPermissions(this,
                    PERMISSION_READ_EXTERNAL,
                    REQUEST_READ_STORAGE);
        }
        return isAllow;
    }
}
