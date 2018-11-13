package com.example.framgianguyenvanthanhd.music_professional.screens.offline.playlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

import java.util.List;

/**
 * Created by MyPC on 28/01/2018.
 */

public class PlaylistOffAdapter extends RecyclerView.Adapter<PlaylistOffAdapter.AlbumHolder> {
    public static final int DEFAULT_POSITION_ADD = 0;
    private List<PlaylistOffline> mPlaylistOfflines;
    private OnClickPlaylistOffListener mAlbumListener;

    public PlaylistOffAdapter(List<PlaylistOffline> playlistOfflines) {
        mPlaylistOfflines = playlistOfflines;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);
        return new AlbumHolder(view);
    }

    public void removeAlbum(PlaylistOffline playlistOffline) {
        int position = mPlaylistOfflines.indexOf(playlistOffline);
        if (position == -1) {
            return;
        }
        mPlaylistOfflines.remove(position);
        notifyItemRemoved(position);
    }

    public void insertAlbum(PlaylistOffline playlistOffline) {
        if (playlistOffline == null) {
            return;
        }
        mPlaylistOfflines.add(mPlaylistOfflines.size(), playlistOffline);
        notifyItemInserted(mPlaylistOfflines.size());
    }

    public void updateItem(PlaylistOffline playlistOffline, String newName) {
        int position = mPlaylistOfflines.indexOf(playlistOffline);
        if (position == -1) {
            return;
        }
        mPlaylistOfflines.remove(position);
        mPlaylistOfflines.add(position, new PlaylistOffline(playlistOffline.getId(), newName));
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        holder.bindData(mPlaylistOfflines.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaylistOfflines != null ? mPlaylistOfflines.size() : 0;
    }

    public void setAlbumListener(OnClickPlaylistOffListener albumListener) {
        mAlbumListener = albumListener;
    }

    /**
     * Holder PlaylistOffline recyler
     */
    public class AlbumHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewNameAlbum;
        private ImageView mButtonMore;

        public AlbumHolder(View itemView) {
            super(itemView);
            mTextViewNameAlbum = itemView.findViewById(R.id.text_name_album);
            mButtonMore = itemView.findViewById(R.id.image_more_album);

            mButtonMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(view);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAlbumListener.onItemClickAlbum(mPlaylistOfflines.get(getAdapterPosition()));
                }
            });
        }

        public void bindData(PlaylistOffline playlistOffline) {
            if (playlistOffline == null) {
                return;
            }
            mTextViewNameAlbum.setText(playlistOffline.getNameAlbum());
        }

        private void showMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), mButtonMore);
            popup.inflate(R.menu.menu_album);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_update_album:
                            mAlbumListener.onUpdateNameAlbum(mPlaylistOfflines.get(getAdapterPosition()));
                            break;
                        case R.id.menu_delete_album:
                            mAlbumListener.onClickDeleteAlbum(mPlaylistOfflines.get(getAdapterPosition()));
                            break;
                    }
                    return true;
                }
            });
            popup.show();
        }
    }
}
