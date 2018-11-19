package com.example.framgianguyenvanthanhd.music_professional.screens.offline;

import android.support.annotation.IntDef;

import static com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainType.PLAYLIST;
import static com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainType.ALL_SONG;
import static com.example.framgianguyenvanthanhd.music_professional.screens.offline.MainType.FAVORITE;


/**
 * Created by MyPC on 22/01/2018.
 */

@IntDef({ALL_SONG, PLAYLIST, FAVORITE})
public @interface MainType {
    int ALL_SONG = 0;
    int PLAYLIST = 1;
    int FAVORITE = 2;
    int DOWNLOAD = 3;
}
