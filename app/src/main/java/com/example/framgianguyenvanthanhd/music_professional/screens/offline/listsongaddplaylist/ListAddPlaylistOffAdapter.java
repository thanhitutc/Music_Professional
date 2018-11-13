package com.example.framgianguyenvanthanhd.music_professional.screens.offline.listsongaddplaylist;

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
 * Created by MyPC on 02/02/2018.
 */

public class ListAddPlaylistOffAdapter
        extends RecyclerView.Adapter<ListAddPlaylistOffAdapter.SongAddToAlbumHolder> {
    private List<SongOffline> mSongOfflines;
    private List<SongOffline> mSongOfflineSelected = new ArrayList<>();

    public ListAddPlaylistOffAdapter(List<SongOffline> songOfflines) {
        mSongOfflines = songOfflines;
    }

    @Override
    public SongAddToAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song_add, parent, false);
        return new SongAddToAlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(SongAddToAlbumHolder holder, int position) {
        holder.bindData(mSongOfflines.get(position));
    }

    public List<SongOffline> getSongOfflineSelected() {
        return mSongOfflineSelected;
    }

    @Override
    public int getItemCount() {
        return mSongOfflines != null ? mSongOfflines.size() : 0;
    }

    /**
     * Holder recycler list song add to favorite
     */
    public class SongAddToAlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewSinger;
        private CheckBox mCheckBox;

        public SongAddToAlbumHolder(final View itemView) {
            super(itemView);
            initComponents();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCheckBox.isChecked()) {
                        mSongOfflineSelected.remove(mSongOfflines.get(getAdapterPosition()));
                        mCheckBox.setChecked(false);
                    } else {
                        mSongOfflineSelected.add(mSongOfflines.get(getAdapterPosition()));
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
