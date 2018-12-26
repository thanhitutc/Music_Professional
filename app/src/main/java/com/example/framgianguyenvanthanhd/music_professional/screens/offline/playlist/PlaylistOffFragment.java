package com.example.framgianguyenvanthanhd.music_professional.screens.offline.playlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PlaylistOfflineRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongInPlaylistRepository;
import com.example.framgianguyenvanthanhd.music_professional.helper.GridSpacingItemDecoration;
import com.example.framgianguyenvanthanhd.music_professional.screens.offline.detailplaylistoff.DetailPlaylistOffActivity;

import java.util.List;

/**
 * Fragment album show list album
 */

public class PlaylistOffFragment extends Fragment
        implements ContractPlaylistOff.AlbumView, OnClickPlaylistOffListener, View.OnClickListener {
    public static final int SPAN_COUNT = 2;
    private ContractPlaylistOff.AlbumPresenter mPresenter;
    private RecyclerView mRecyclerViewAlbum;
    private PlaylistOffAdapter mAdapter;
    private FloatingActionButton mButtonAddAlbum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadListAlbum();
    }

    @Override
    public void setPresenter(ContractPlaylistOff.AlbumPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListAlbum(List<PlaylistOffline> playlistOfflines) {
        mAdapter = new PlaylistOffAdapter(playlistOfflines);
        mRecyclerViewAlbum.setAdapter(mAdapter);
        mAdapter.setAlbumListener(this);
    }

    @Override
    public void showNoListAlbum() {

    }

    @Override
    public void showNewAlbumSuccess(PlaylistOffline playlistOffline) {
        mAdapter.insertAlbum(playlistOffline);
    }

    @Override
    public void showNewAlbumFail() {

    }

    @Override
    public void showDeleteAlbumSuccess(PlaylistOffline playlistOffline) {
        mAdapter.removeAlbum(playlistOffline);
    }

    @Override
    public void showDeleteAlbumFail() {

    }

    @Override
    public void showUpdateNameSuccess(PlaylistOffline playlistOffline, String newName) {
        mAdapter.updateItem(playlistOffline, newName);
    }

    @Override
    public void showUpdateNameFail() {

    }

    @Override
    public void onItemClickAlbum(PlaylistOffline playlistOffline) {
        startActivity(DetailPlaylistOffActivity.getInstance(getActivity(), playlistOffline));
    }

    @Override
    public void onClickDeleteAlbum(PlaylistOffline playlistOffline) {
        createDialogDelete(playlistOffline);
    }

    @Override
    public void onUpdateNameAlbum(PlaylistOffline playlistOffline) {
        createDialogUpdateNameAlbum(playlistOffline);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_album:
                createDialogAddAlbum();
                break;
        }
    }

    private void initPresenter() {
        PlaylistOfflineRepository playlistOfflineRepository = PlaylistOfflineRepository.getInstance(getActivity());
        SongInPlaylistRepository songInPlaylistRepository =
                SongInPlaylistRepository.getInstance(getActivity());
        mPresenter = new PlaylistOffPresenter(playlistOfflineRepository, songInPlaylistRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    private void initRecyclerView() {
        mButtonAddAlbum = getView().findViewById(R.id.button_add_album);
        mButtonAddAlbum.setOnClickListener(this);
        mRecyclerViewAlbum = getView().findViewById(R.id.recycler_album);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        mRecyclerViewAlbum.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(getContext(),10), true));
        mRecyclerViewAlbum.setLayoutManager(layoutManager);
    }

    private void createDialogAddAlbum() {
        LayoutInflater inflater = this.getLayoutInflater();
        final View al = inflater.inflate(R.layout.layout_dialog_add_playlist, null);
        final EditText editTextNewAlbum = al.findViewById(R.id.edit_text_new_album);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.title_dialog_add_album));
        dialog.setView(al);
        dialog.setNegativeButton(getActivity().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.addNewAlbum(editTextNewAlbum.getText().toString());
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

    private void createDialogUpdateNameAlbum(final PlaylistOffline playlistOffline) {
        LayoutInflater inflater = this.getLayoutInflater();
        final View al = inflater.inflate(R.layout.layout_dialog_add_playlist, null);
        final EditText editTextNewName = al.findViewById(R.id.edit_text_new_album);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.title_dialog_rename_album));
        dialog.setView(al);
        dialog.setNegativeButton(getActivity().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.updateNameAlbum(playlistOffline, editTextNewName.getText().toString());
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

    private void createDialogDelete(final PlaylistOffline playlistOffline) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.title_dialog_delete_album));
        dialog.setMessage(getActivity().getResources().getString(R.string.announcement_delete_album)
                + " "
                + playlistOffline.getNameAlbum()
                + "?");
        dialog.setNegativeButton(getActivity().getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteAlbum(playlistOffline);
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
}
