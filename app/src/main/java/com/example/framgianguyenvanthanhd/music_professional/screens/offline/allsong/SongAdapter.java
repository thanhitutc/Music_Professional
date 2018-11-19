package com.example.framgianguyenvanthanhd.music_professional.screens.offline.allsong;

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
 * Created by MyPC on 25/01/2018.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongRecyclerViewHolder> {
    private List<SongOffline> mSongOfflines;
    private OnClickSongListener mListener;

    public SongAdapter(List<SongOffline> songOfflines) {
        mSongOfflines = songOfflines;
    }

    @Override
    public SongRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongRecyclerViewHolder holder, int position) {
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

    public void setListener(OnClickSongListener listener) {
        mListener = listener;
    }

    /**
     * SongOffline recycler holder
     */

    public class SongRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle;
        private TextView mTextSinger;
        private ImageView mButtonMore;

        public SongRecyclerViewHolder(View itemView) {
            super(itemView);
            initComponents();
            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickSong(mSongOfflines, getAdapterPosition());
                }
            });
        }

        private void initComponents() {
            mButtonMore = itemView.findViewById(R.id.image_more_song);
            mTextTitle = itemView.findViewById(R.id.text_title_song);
            mTextSinger = itemView.findViewById(R.id.text_singer_song);
        }

        public void bindData(SongOffline songOffline) {
            if (songOffline == null) {
                return;
            }
            mTextTitle.setText(songOffline.getTitle());
            mTextSinger.setText(songOffline.getSinger());
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mButtonMore);
            popup.inflate(R.menu.menu_item_song);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_add_favorite:
                            mListener.onAddToFavorite(mSongOfflines.get(getAdapterPosition()));
                            break;
                        case R.id.menu_add_to_album:
                            mListener.onAddToAlbum(mSongOfflines.get(getAdapterPosition()));
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
