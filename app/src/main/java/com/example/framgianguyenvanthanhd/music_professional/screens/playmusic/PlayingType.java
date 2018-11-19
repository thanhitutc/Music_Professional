package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic;

import android.support.annotation.IntDef;

import static com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.PlayingType.DISC;
import static com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.PlayingType.LIST;

/**
 * Created by admin on 11/19/2018.
 */

@IntDef({LIST, DISC})
public @interface PlayingType {
    int LIST = 0;
    int DISC = 1;
}
