package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddfavorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongOffline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPC on 01/02/2018.
 */

public class SongAddToFavoriteAdapter
        extends RecyclerView.Adapter<SongAddToFavoriteAdapter.SongAddToFavoriteHolder> {
    private List<SongOffline> mSongOfflines;
    private List<SongOffline> mSongOfflineCheck = new ArrayList<>();

    public SongAddToFavoriteAdapter(List<SongOffline> songOfflines) {
        mSongOfflines = songOfflines;
    }

    @Override
    public SongAddToFavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song_add, parent, false);
        return new SongAddToFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(SongAddToFavoriteHolder holder, int position) {
        holder.bindData(mSongOfflines.get(position));
    }

    public List<SongOffline> getSongOfflineCheck() {
        return mSongOfflineCheck;
    }

    @Override
    public int getItemCount() {
        return mSongOfflines != null ? mSongOfflines.size() : 0;
    }

    /**
     * Holder recycler list song add to favorite
     */
    public class SongAddToFavoriteHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private CheckBox mCheckBox;

        public SongAddToFavoriteHolder(final View itemView) {
            super(itemView);
            initComponents();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCheckBox.isChecked()) {
                        mSongOfflineCheck.remove(mSongOfflines.get(getAdapterPosition()));
                        mCheckBox.setChecked(false);
                    } else {
                        mSongOfflineCheck.add(mSongOfflines.get(getAdapterPosition()));
                        mCheckBox.setChecked(true);
                    }
                }
            });
        }

        private void initComponents() {
            mTextViewTitle = itemView.findViewById(R.id.text_title_song);
            mTextViewSinger = itemView.findViewById(R.id.text_singer_song);
            mCheckBox = itemView.findViewById(R.id.checkbox_song);
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
