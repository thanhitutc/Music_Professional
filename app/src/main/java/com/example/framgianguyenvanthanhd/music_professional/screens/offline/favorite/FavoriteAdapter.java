package com.example.framgianguyenvanthanhd.music_professional.screens.offline.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private static final int DEFAULT_INSERT = 0;
    private List<SongOffline> mSongOfflines;
    private OnClickSongFavoriteListener mListener;

    public FavoriteAdapter(List<SongOffline> songOfflines) {
        mSongOfflines = songOfflines;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_song, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        holder.bindData(mSongOfflines.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongOfflines != null ? mSongOfflines.size() : 0;
    }

    public void removeSongFavorite(SongOffline songOffline) {
        int position = mSongOfflines.indexOf(songOffline);
        if (position == -1) {
            return;
        }
        mSongOfflines.remove(position);
        notifyItemRemoved(position);
    }

    public void insertSongFavorite(SongOffline songOffline) {
        if (songOffline == null) {
            return;
        }
        mSongOfflines.add(DEFAULT_INSERT, songOffline);
        notifyItemInserted(DEFAULT_INSERT);
    }

    public void setListener(OnClickSongFavoriteListener listener) {
        mListener = listener;
    }

    /**
     * Favorite adapter holder
     */
    public class FavoriteHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private ImageView mButtonDelete;

        public FavoriteHolder(View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.text_title_song_favorite);
            mTextViewSinger = itemView.findViewById(R.id.text_singer_song_favorite);
            mButtonDelete = itemView.findViewById(R.id.image_delete_favorite);

            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteFavoriteSong(mSongOfflines.get(getAdapterPosition()));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickFavoriteSong(mSongOfflines, getAdapterPosition());
                }
            });
        }

        public void bindData(SongOffline songOffline) {
            if (songOffline == null) {
                return;
            }
            mTextViewTitle.setText(songOffline.getTitle());
            mTextViewSinger.setText(songOffline.getSinger());
        }
    }
}
