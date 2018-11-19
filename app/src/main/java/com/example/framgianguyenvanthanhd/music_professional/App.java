package com.example.framgianguyenvanthanhd.music_professional;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 11/12/2018.
 */

public class App extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }
}
