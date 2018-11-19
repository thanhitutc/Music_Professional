package com.example.framgianguyenvanthanhd.music_professional.data.datasource.offline;


import com.example.framgianguyenvanthanhd.music_professional.data.model.PlaylistOffline;

import java.util.List;

/**
 * Created by MyPC on 25/01/2018.
 */

public interface PlaylistOffDataSource {

    List<PlaylistOffline> getListAlbum();

    boolean insertAlbum(String name);

    boolean deleteAlbum(int idAlbum);

    boolean updateName(int idAlbum, String newName);

    int getLastIdInsert();
}
