package com.example.framgianguyenvanthanhd.music_professional.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.framgianguyenvanthanhd.music_professional.MainActivity;
import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants;
import com.example.framgianguyenvanthanhd.music_professional.data.model.Setting;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SettingRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongPlayingRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.SettingLocalDataSource;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.song_playing.ContractSongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.song_playing.SongPlayingPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantIntent.EXTRA_INIT_SONG_SERVICE;
import static com.example.framgianguyenvanthanhd.music_professional.service.NotificationType.REQUEST_CODE_CLEAR;
import static com.example.framgianguyenvanthanhd.music_professional.service.NotificationType.REQUEST_CODE_NEXT;
import static com.example.framgianguyenvanthanhd.music_professional.service.NotificationType.REQUEST_CODE_PAUSE;
import static com.example.framgianguyenvanthanhd.music_professional.service.NotificationType.REQUEST_CODE_PREVIOUS;

/**
 * Created by MyPC on 30/01/2018.
 */

public class MediaService extends Service implements BaseMediaPlayer, ContractSongPlaying.SongPlayingView {
    private static final String ACTION_CHANGE_MEDIA_NEXT = "ACTION_CHANGE_MEDIA_NEXT";
    private static final String ACTION_CHANGE_MEDIA_PREVIOUS = "ACTION_CHANGE_MEDIA_PREVIOUS";
    private static final String ACTION_CHANGE_MEDIA_STATE = "ACTION_CHANGE_MEDIA_STATE";
    private static final String ACTION_MEDIA_CLEAR = "ACTION_MEDIA_CLEAR";
    private static final int DEFAULT_POSITION_START = 0;
    private static final int ID_NOTIFICATION = 183;
    private List<SongPlaying> mSongsPlaying;
    private List<SongPlaying> mSongsShuffled;
    private MediaPlayer mMediaPlayer;
    private int mPosition;
    private int mPositionShuffled;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private Intent mIntentBroadcast;
    private Setting mSetting;
    private SettingRepository mSettingRepository;
    private SongPlayingRepository mPlayingRepository;
    private ContractSongPlaying.SongPlayingPresenter mPresenter;

    public static Intent getInstance(Context context, SongPlaying songPlaying, int position) {
        Intent intent = new Intent(context, MediaService.class);
        intent.setAction(Constants.ConstantIntent.ACTION_INIT_SONG_SERVICE);
        intent.putExtra(EXTRA_INIT_SONG_SERVICE, songPlaying);
        intent.putExtra(Constants.ConstantIntent.EXTRA_INIT_POSITION_SONG_SERVICE, position);
        return intent;
    }

    @Override
    public void setPresenter(ContractSongPlaying.SongPlayingPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void songsPlayingSuccess(List<SongPlaying> songPlaying) {
        if (mSongsPlaying != null) {
            mSongsPlaying.clear();
        }
        mSongsPlaying = songPlaying;
    }

    @Override
    public void songPlayingFail() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        initSettingService();
        mIntentBroadcast = new Intent();
        mIntentBroadcast.setAction(Constants.ConstantBroadcast.ACTION_STATE_MEDIA);
        mPlayingRepository = SongPlayingRepository.getInstance(this);
        mPresenter = new SongPlayingPresenter(mPlayingRepository);
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    private void initSettingService() {
        mSettingRepository =
                SettingRepository.getInstance(SettingLocalDataSource.getInstance(this));
        mSetting = mSettingRepository.getSetting();
    }

    @Override
    public void onDestroy() {
        release();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action == null) {
            return START_STICKY;
        }
        switch (action) {
            case Constants.ConstantIntent.ACTION_INIT_SONG_SERVICE:
                SongPlaying songPlaying = (SongPlaying) intent.getSerializableExtra(EXTRA_INIT_SONG_SERVICE);
                mPlayingRepository.insertSongPlaying(songPlaying);
                mPresenter.getSongsPlaying();
                mPosition = mSongsPlaying.indexOf(songPlaying);
                mSongsPlaying.add(songPlaying);
                if (mSetting.isShuffleMode()) {
                    shuffleSong();
                    play(mPositionShuffled);
                } else {
                    play(mPosition);
                }
                break;
            case ACTION_CHANGE_MEDIA_PREVIOUS:
                previous();
                break;
            case ACTION_CHANGE_MEDIA_STATE:
                if (isPlay()) {
                    pause();
                } else {
                    resume();
                }
                break;
            case ACTION_CHANGE_MEDIA_NEXT:
                next();
                break;
            case ACTION_MEDIA_CLEAR:
                stopSelf();
                stopForeground(true);
                stop();
                break;
        }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new MediaBinder();
    }

    @Override
    public void start() {
        mMediaPlayer.start();
        mIntentBroadcast.putExtra(Constants.ConstantBroadcast.EXTRA_STATE_MEDIA, true);
        sendBroadcast(mIntentBroadcast);
    }

    @Override
    public void play(int position) {
        mMediaPlayer.reset();
        try {
            List<SongPlaying> playings = new ArrayList<>();
            boolean shuffleMode = mSetting.isShuffleMode();
            if (shuffleMode) {
                playings = mSongsShuffled;
            } else {
                playings = mSongsPlaying;
            }

            switch (playings.get(position).getMode()) {
                case ONLINE:
                    Uri uri = Uri.parse(playings.get(position).getResource());
                    mMediaPlayer.setDataSource(this, uri);
                    break;
                case OFFLINE:
                    mMediaPlayer.setDataSource(playings.get(position).getResource());
                    break;
            }
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Logger.getLogger(e.toString());
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mIntentBroadcast.putExtra(Constants.ConstantBroadcast.EXTRA_STATE_MEDIA, false);
            sendBroadcast(mIntentBroadcast);
        }
        updateNotification();
    }

    @Override
    public void resume() {
        if (isPlay()) {
            return;
        }
        start();
        updateNotification();
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mIntentBroadcast.putExtra(Constants.ConstantBroadcast.EXTRA_STATE_MEDIA, false);
            sendBroadcast(mIntentBroadcast);
        }
        updateNotification();
    }

    @Override
    public void release() {
        stop();
        mMediaPlayer.release();
    }

    @Override
    public void seekTo(int index) {
        mMediaPlayer.seekTo(index);
    }

    @Override
    public void next() {
        checkShuffleMode();
    }

    @Override
    public void previous() {
        if (!mSetting.isShuffleMode()) {
            if (mPosition > DEFAULT_POSITION_START) {
                mPosition--;
            } else {
                mPosition = DEFAULT_POSITION_START;
            }
            play(mPosition);
        } else {
            if (mPositionShuffled > DEFAULT_POSITION_START) {
                mPositionShuffled--;
            } else {
                mPositionShuffled = DEFAULT_POSITION_START;
            }
            play(mPositionShuffled);
        }
    }

    @Override
    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public long getCurrentDuration() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public boolean isPlay() {
        return mMediaPlayer.isPlaying();
    }

    public boolean isShuff() {
        return mSetting.isShuffleMode();
    }

    public void setShuff(boolean shuff) {
        mSetting.setShuffleMode(shuff);
        mSettingRepository.saveSetting(mSetting);
    }

    public void shuffleSong() {
        mSongsShuffled = new ArrayList<>(mSongsPlaying);
        Collections.shuffle(mSongsShuffled);
        mPositionShuffled = mSongsShuffled.indexOf(mSongsPlaying.get(mPosition));
    }

    public void unShuffleSong() {
        mPosition = mSongsPlaying.indexOf(mSongsShuffled.get(mPositionShuffled));
    }

    private void checkShuffleMode() {
        boolean shuffleMode = mSetting.isShuffleMode();
        if (shuffleMode) {
            if (mPositionShuffled < mSongsShuffled.size() - 1) {
                mPositionShuffled++;
            } else {
                mPositionShuffled = DEFAULT_POSITION_START;
            }
            play(mPositionShuffled);
        } else {
            if (mPosition < mSongsPlaying.size() - 1) {
                mPosition++;
            } else {
                mPosition = DEFAULT_POSITION_START;
            }
            play(mPosition);
        }
    }

    public int getRepeat() {
        return mSetting.getRepeatMode();
    }

    public void setRepeat(int repeat) {
        mSetting.setRepeatMode(repeat);
        mSettingRepository.saveSetting(mSetting);
    }

    public List<SongPlaying> getSongsPlaying() {
        if (mSetting.isShuffleMode()) {
            return mSongsShuffled;
        }
        return mSongsPlaying;
    }

    public void playWithSongClick(SongPlaying songPlaying) {
        if (mSetting.isShuffleMode()) {
            mPositionShuffled = mSongsShuffled.indexOf(songPlaying);
            play(mPositionShuffled);
        } else {
            mPosition = mSongsPlaying.indexOf(songPlaying);
            play(mPosition);
        }
    }

    public SongPlaying getSongPlaying() {
        if (mSetting.isShuffleMode()) {
            return mSongsShuffled.get(mPositionShuffled);
        }
        return mSongsPlaying.get(mPosition);
    }

    private void initMediaPlayer() {
        mPosition = -1;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(mOnPrepare);
        mMediaPlayer.setOnCompletionListener(mOnComple);
    }

    private void checkShuffleAndRepeatMode() {
        switch (mSetting.getRepeatMode()) {
            case RepeatType.NO_REPEAT:
                if (!mSetting.isShuffleMode()) {
                    if (mPosition < mSongsPlaying.size() - 1) {
                        mPosition++;
                        play(mPosition);
                    } else {
                        mPosition = DEFAULT_POSITION_START;
                        seekTo(DEFAULT_POSITION_START);
                        stop();
                    }
                } else {
                    if (mPositionShuffled < mSongsShuffled.size() - 1) {
                        mPositionShuffled++;
                        play(mPositionShuffled);
                    } else {
                        mPositionShuffled = DEFAULT_POSITION_START;
                        seekTo(DEFAULT_POSITION_START);
                        stop();
                    }
                }
                break;
            case RepeatType.REPEAT_ONE:
                if (mSetting.isShuffleMode()) {
                    play(mPositionShuffled);
                } else {
                    play(mPosition);
                }
                break;
            case RepeatType.REPEAT_ALL:
                if (!mSetting.isShuffleMode()) {
                    if (mPosition == mSongsPlaying.size() - 1) {
                        mPosition = DEFAULT_POSITION_START;
                    } else {
                        mPosition++;
                    }
                    play(mPosition);
                } else {
                    if (mPositionShuffled == mSongsShuffled.size() - 1) {
                        mPositionShuffled = DEFAULT_POSITION_START;
                    } else {
                        mPositionShuffled++;
                    }
                    play(mPositionShuffled);
                }
                break;
        }
    }

    private MediaPlayer.OnPreparedListener mOnPrepare = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            start();
            if (!mSetting.isShuffleMode()) {
                createNotification(mSongsPlaying.get(mPosition).getName());
            } else {
                createNotification(mSongsShuffled.get(mPositionShuffled).getName());
            }
        }
    };

    private MediaPlayer.OnCompletionListener mOnComple = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            checkShuffleAndRepeatMode();
        }
    };

    public String getTitleSongPlaying() {
        if (!mSetting.isShuffleMode()) {
            return mSongsPlaying.get(mPosition).getName() + " - " + mSongsPlaying.get(mPosition).getSinger();
        }
        return mSongsShuffled.get(mPositionShuffled).getName() + " - " + mSongsShuffled.get(
                mPositionShuffled).getSinger();
    }

    private void createNotification(String title) {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        mRemoteViews.setTextViewText(R.id.text_title_song_notification, title);

        Intent intentActionNext = new Intent();
        intentActionNext.setAction(ACTION_CHANGE_MEDIA_NEXT);
        intentActionNext.setClass(getApplicationContext(), MediaService.class);
        PendingIntent peServiceNext =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_NEXT,
                        intentActionNext, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_next, peServiceNext);

        Intent intentActionPrev = new Intent();
        intentActionPrev.setAction(ACTION_CHANGE_MEDIA_PREVIOUS);
        intentActionPrev.setClass(getApplicationContext(), MediaService.class);
        PendingIntent peServicePre =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_PREVIOUS,
                        intentActionPrev, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_pre, peServicePre);

        Intent intentActionPause = new Intent();
        intentActionPause.setAction(ACTION_CHANGE_MEDIA_STATE);
        intentActionPause.setClass(getApplicationContext(), MediaService.class);
        PendingIntent peServicePause =
                PendingIntent.getService(getApplicationContext(), REQUEST_CODE_PAUSE,
                        intentActionPause, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_pause, peServicePause);

        Intent intentActionClear = new Intent();
        intentActionClear.setAction(ACTION_MEDIA_CLEAR);
        intentActionClear.setClass(this, MediaService.class);
        PendingIntent peServiceClear =
                PendingIntent.getService(this, REQUEST_CODE_CLEAR, intentActionClear,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.image_cancel, peServiceClear);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivities(this, (int) System.currentTimeMillis(),
                        new Intent[]{intent}, 0);
        Notification.Builder notificationBuilder =
                new Notification.Builder(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = notificationBuilder.setSmallIcon(R.drawable.icon_notification)
                    .setContentIntent(pendingIntent)
                    .setContent(mRemoteViews)
                    .setDefaults(Notification.FLAG_NO_CLEAR)
                    .build();
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    private void updateNotification() {
        if (isPlay()) {
            mRemoteViews.setImageViewResource(R.id.image_pause, R.drawable.ic_pause);
        } else {
            mRemoteViews.setImageViewResource(R.id.image_pause, R.drawable.ic_play);
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    /**
     * Media Binder
     */
    public class MediaBinder extends Binder {
        public MediaService getMediaService() {
            return MediaService.this;
        }
    }
}