package com.example.framgianguyenvanthanhd.music_professional.screens.offline.favorite;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddfavorite.ListSongAddActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantBroadcast.ACTION_UPDATE_FAVORITE;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantBroadcast.EXTRA_SONG_UPDATE_FAVORITE;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantPermission.REQUEST_READ_STORAGE;
import static com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong.AllSongFragment.PERMISSION_READ_EXTERNAL;

/**
 * Favorite fragment show list
 */
public class FavoriteFragment extends Fragment
        implements ContractFavorite.FavoriteView, OnClickSongFavoriteListener,
        View.OnClickListener {
    private RecyclerView mRecyclerViewFavorite;
    private FavoriteAdapter mAdapter;
    private ContractFavorite.FavoritePresenter mPresenter;
    private FloatingActionButton mButtonAddSong;
    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initPresenter();
        receiveBroadcastUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            mPresenter.loadListFavorite();
        }
        getActivity().registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void setPresenter(ContractFavorite.FavoritePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListSong(List<SongOffline> songOfflines) {
        mAdapter = new FavoriteAdapter(songOfflines);
        mRecyclerViewFavorite.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    public void showNoSong() {

    }

    @Override
    public void showDeleteSongSuccess(SongOffline songOffline) {
        mAdapter.removeSongFavorite(songOffline);
    }

    @Override
    public void showDeleteSongFail() {
        showToast(getActivity().getResources().getString(R.string.announcement_delete_song_fail));
    }

    @Override
    public void onItemClickFavoriteSong(List<SongOffline> songOfflines, int position) {
//        getActivity().startActivity(PlayMusicActivity.getInstance(getActivity()));
//        getActivity().startService(MediaService.getInstance(getActivity(), songOfflines, position));
    }

    @Override
    public void onDeleteFavoriteSong(SongOffline songOffline) {
        createDialogDelete(songOffline);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_favorite:
                getActivity().startActivity(ListSongAddActivity.getInstance(getActivity()));
                break;
        }
    }

    private void initPresenter() {
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance();
        mPresenter = new FavoritePresenter(favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    private void initRecyclerView() {
        mButtonAddSong = getView().findViewById(R.id.button_add_favorite);
        mButtonAddSong.setOnClickListener(this);
        mRecyclerViewFavorite = getView().findViewById(R.id.recycler_favorite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewFavorite.setLayoutManager(layoutManager);
    }

    private void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    private void createDialogDelete(final SongOffline songOffline) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.title_dialog_delete_song));
        dialog.setMessage(getActivity().getResources().getString(R.string.announcement_delete_song)
                + " "
                + songOffline.getTitle()
                + "?");
        dialog.setNegativeButton(getActivity().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteSongFavorite(songOffline);
                    }
                });
        dialog.setPositiveButton(getActivity().getResources().getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }

    private void receiveBroadcastUpdate() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ACTION_UPDATE_FAVORITE);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(ACTION_UPDATE_FAVORITE)) {
                    SongOffline songOffline = intent.getParcelableExtra(
                            EXTRA_SONG_UPDATE_FAVORITE);
                    if (mAdapter == null) {
                        List<SongOffline> songOfflines = new ArrayList<>();
                        mAdapter = new FavoriteAdapter(songOfflines);
                    }
                    mAdapter.insertSongFavorite(songOffline);
                }
            }
        };
    }

    private boolean checkPermission() {
        boolean isAllow = true;
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            isAllow = false;
            ActivityCompat.requestPermissions((Activity) getActivity(),
                    PERMISSION_READ_EXTERNAL,
                    REQUEST_READ_STORAGE);
        }
        return isAllow;
    }
}
