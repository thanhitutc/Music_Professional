package com.example.framgianguyenvanthanhd.music_professional.screens.offline.addsongtoplaylist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

import java.util.List;

/**
 * Created by MyPC on 26/01/2018.
 */

public class ListPlaylistAddSongAdapter
        extends RecyclerView.Adapter<ListPlaylistAddSongAdapter.ListAlbumHolder> {
    private List<PlaylistOffline> mPlaylistOfflines;
    private OnClickPlaylistOffListener mListener;

    public ListPlaylistAddSongAdapter(List<PlaylistOffline> playlistOfflines) {
        mPlaylistOfflines = playlistOfflines;
    }

    @Override
    public ListAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist_add, parent, false);
        return new ListAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAlbumHolder holder, int position) {
        holder.binData(mPlaylistOfflines.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaylistOfflines != null ? mPlaylistOfflines.size() : 0;
    }

    public void setListener(OnClickPlaylistOffListener listener) {
        mListener = listener;
    }

    /**
     * Holder List album add
     */

    public class ListAlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewNameAlbum;

        public ListAlbumHolder(View itemView) {
            super(itemView);
            mTextViewNameAlbum = itemView.findViewById(R.id.text_name_album);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickSong(mPlaylistOfflines.get(getAdapterPosition()));
                }
            });
        }

        public void binData(PlaylistOffline playlistOffline) {
            if (playlistOffline == null) {
                return;
            }
            mTextViewNameAlbum.setText(playlistOffline.getNameAlbum());
        }
    }
}
