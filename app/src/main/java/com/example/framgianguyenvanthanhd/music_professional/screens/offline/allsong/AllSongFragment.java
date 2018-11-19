package com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.Utils.SongMode;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.FavoriteRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongRepository;
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.addsongtoplaylist.SongAddToPlaylistOffActivity;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.PlayMusicActivity;
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService;

import java.util.List;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantBroadcast.ACTION_UPDATE_FAVORITE;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantBroadcast.EXTRA_SONG_UPDATE_FAVORITE;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.ACTION_ID_SONG_ADD_TO_ALBUM;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.EXTRA_ID_SONG_ADD_TO_ALBUM;
import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantPermission.REQUEST_READ_STORAGE;

/**
 * AllSongFragment
 **/
public class AllSongFragment extends Fragment
        implements AllSongContract.SongView, OnClickSongListener {
    private AllSongContract.Presenter mPresenter;
    private SongAdapter mSongAdapter;
    private RecyclerView mRecycler;

    public static final String[] PERMISSION_READ_EXTERNAL =
            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_allsong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
        requestPermission();
        if (checkPermission()){
            initPresenter();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(AllSongContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListSong(List<SongOffline> songOfflines) {
        mSongAdapter = new SongAdapter(songOfflines);
        mRecycler.setAdapter(mSongAdapter);
        mSongAdapter.setListener(this);
    }

    @Override
    public void showNoSong() {
        showToast(getActivity().getResources().getString(R.string.announcement_no_song));
    }

    @Override
    public void showDeleteSuccess(SongOffline songOffline) {
        mSongAdapter.removeSong(songOffline);
    }

    @Override
    public void showDeleteError() {
        showToast(getActivity().getResources().getString(R.string.announcement_delete_song_fail));
    }

    @Override
    public void showAddFavoriteSuccess(SongOffline songOffline) {
        showToast(getActivity().getResources()
                .getString(R.string.announcement_add_song_favorite_success));
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_FAVORITE);
        intent.putExtra(EXTRA_SONG_UPDATE_FAVORITE, (Parcelable) songOffline);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void showAddFavoriteError() {
        showToast(getActivity().getResources()
                .getString(R.string.announcement_add_song_favorite_fail));
    }

    @Override
    public void onItemClickSong(List<SongOffline> songOfflines, int position) {
        getActivity().startActivity(PlayMusicActivity.getInstance(getActivity()));
        SongOffline songOffline = songOfflines.get(position);
        SongPlaying songPlaying = new SongPlaying(songOffline.getId(), songOffline.getTitle(), songOffline.getSinger(),
                null, songOffline.getData(), SongMode.OFFLINE);
        getActivity().startService(MediaService.getInstance(getActivity(), songPlaying, position));
    }

    @Override
    public void onAddToFavorite(SongOffline songOffline) {
        mPresenter.addToFavorite(songOffline);
    }

    @Override
    public void onAddToAlbum(SongOffline songOffline) {
        Intent intent = new Intent(getActivity(), SongAddToPlaylistOffActivity.class);
        intent.setAction(ACTION_ID_SONG_ADD_TO_ALBUM);
        intent.putExtra(EXTRA_ID_SONG_ADD_TO_ALBUM, songOffline.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public void onDeleteSong(SongOffline songOffline) {
        createDialogDelete(songOffline);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length == 1) {
            initPresenter();
            Log.e("DONG Y","ROI");
        } else {
            requestPermission();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        mRecycler = getView().findViewById(R.id.recyler_allsong);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(layoutManager);
    }

    private void initPresenter() {
        SongRepository songRepository = SongRepository.getInstance(getActivity());
        FavoriteRepository favoriteRepository = FavoriteRepository.getInstance();
        mPresenter = new AllSongPresenter(songRepository, favoriteRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
        mPresenter.loadListSong();
    }

    public boolean checkPermission() {
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
                        mPresenter.deleteSong(songOffline);
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

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_READ_STORAGE);
            }
        }
    }
}
