package com.example.framgianguyenvanthanhd.music_professional.service;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.framgianguyenvanthanhd.music_professional.data.download.DownloadApi;
import com.example.framgianguyenvanthanhd.music_professional.data.retrofits.APIService;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.PlayMusicActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadService extends IntentService {

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    public DownloadService() {
        super("DownloadService");
        Log.e("DownloadService", "constructor");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DownloadService", "onCreate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("DownloadService", "onHandleIntent");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW);

            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        String urlSong = intent.getExtras().getString(PlayMusicActivity.URL_SONG_DOWNLOAD);
        String[] urlArr = urlSong.split("/");
        String nameSong = urlArr[urlArr.length - 1];

        notificationBuilder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Preparing download " + nameSong)
                .setDefaults(0)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(100, 0, true)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());


        startDownloadSong(urlSong, nameSong);
    }

    private void updateNotification(int currentProgress, String nameSong) {
        Log.d("Downloaded: " , currentProgress + "");
        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentTitle("Downloading");
        notificationBuilder.setContentText( currentProgress + "% " + nameSong);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void startDownloadSong(String url, final String nameSong) {
        Log.d("Start Download: ", nameSong + " with url: " + url);
        DownloadApi downloadApi = APIService.Companion.downloadSong();
        final Call<ResponseBody> request = downloadApi.downloadSong(url);
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Log.d("Download response ok", nameSong);
                        downloadAndSaveFile(response.body(), nameSong);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onDownloadComplete(false, nameSong);
                Log.e("DownLoad Error " + nameSong + " : ",t.toString());
            }
        });

    }

    private void downloadAndSaveFile(ResponseBody body, String nameSong) throws IOException {
        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), nameSong);
        OutputStream outputStream = new FileOutputStream(outputFile);
        long total = 0;
        boolean downloadComplete = false;
        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        while ((count = inputStream.read(data)) != -1) {

            total += count;
            int progress = (int) ((double) (total * 100) / (double) fileSize);

            updateNotification(progress, nameSong);
            outputStream.write(data, 0, count);
            downloadComplete = true;
        }
        onDownloadComplete(downloadComplete, nameSong);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private void sendProgressUpdate(boolean downloadComplete) {

//        Intent intent = new Intent(MainActivity.PROGRESS_UPDATE);
//        intent.putExtra("downloadComplete", downloadComplete);
//        LocalBroadcastManager.getInstance(BackgroundNotificationService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(boolean isDownloadAndSaveSuccess, String nameSong) {
//        sendProgressUpdate(downloadComplete);
        if (isDownloadAndSaveSuccess) {
            notificationBuilder.setContentText("Download Complete " + nameSong);
        } else {
            notificationBuilder.setContentText("Download Error " + nameSong);
        }
        notificationBuilder.setContentTitle(null);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setOngoing(false);
        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
        Log.e("thanhd_download", "OnTaskRemove");
    }
}
