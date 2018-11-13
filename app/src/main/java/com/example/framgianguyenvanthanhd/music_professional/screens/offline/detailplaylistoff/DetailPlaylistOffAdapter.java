package com.example.framgianguyenvanthanhd.music_professional.screens.offline.detailplaylistoff;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 29/01/2018.
 */

public class DetailPlaylistOffAdapter extends RecyclerView.Adapter<DetailPlaylistOffAdapter.DetailAlbumHolder> {
    private List<SongOffline> mSongOfflines;
    private OnClickSongDetailPlaylistOffListener mListener;

    public DetailPlaylistOffAdapter(List<SongOffline> songOfflines) {
        mSongOfflines = songOfflines;
    }

    @Override
    public DetailAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new DetailAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailAlbumHolder holder, int position) {
        holder.bindData(mSongOfflines.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongOfflines != null ? mSongOfflines.size() : 0;
    }

    public void removeSong(SongOffline songOffline) {
        int position = mSongOfflines.indexOf(songOffline);
        if (position == -1) {
            return;
        }
        mSongOfflines.remove(position);
        notifyItemRemoved(position);
    }

    public void setListener(OnClickSongDetailPlaylistOffListener listener) {
        mListener = listener;
    }

    /**
     * Holder recycler detail album
     */
    public class DetailAlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private ImageView mButtonMore;

        public DetailAlbumHolder(View itemView) {
            super(itemView);
            initComponents();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickSongDetailAlbum(mSongOfflines, getAdapterPosition());
                }
            });
            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });
        }

        private void initComponents() {
            mTextViewTitle = itemView.findViewById(R.id.text_title_song);
            mTextViewSinger = itemView.findViewById(R.id.text_singer_song);
            mButtonMore = itemView.findViewById(R.id.image_more_song);
        }

        public void bindData(SongOffline songOffline) {
            if (songOffline == null) {
                return;
            }
            mTextViewTitle.setText(songOffline.getTitle());
            mTextViewSinger.setText(songOffline.getSinger());
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mButtonMore);
            popup.inflate(R.menu.menu_song_detail_album);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_add_favorite:
                            mListener.onAddSongToFavorite(mSongOfflines.get(getAdapterPosition()));
                            break;
                        case R.id.menu_delete_song:
                            mListener.onDeleteSong(mSongOfflines.get(getAdapterPosition()));
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }
}
