package com.example.framgianguyenvanthanhd.music_professional.screens.playmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants;
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService;
import com.example.framgianguyenvanthanhd.music_professional.service.RepeatType;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantBroadcast.ACTION_STATE_MEDIA;


/**
 * Created by MyPC on 31/01/2018.
 */

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int DEFAULT_DELAY = 1000;
    private static final String TIME_FORMAT = "mm:ss";
    private static final String TIME_DEFAULT = "00:00";
    private TextView mTextCurrentDuration;
    private TextView mTextDuration;
    private TextView mTextTitleSong;
    private SeekBar mSeekBar;
    private ViewPager mViewPager;
    private ImageView mButtonShuffle;
    private ImageView mButtonPrevious;
    private ImageView mButtonState;
    private ImageView mButtonNext;
    private ImageView mButtonRepeat;
    private MediaService mService;
    private Handler mHandler;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mBroadcastReceiver;
    private Animation mAnimation;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        return intent;
    }

    private ServiceConnection mMediaConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaService.MediaBinder mediaBinder = (MediaService.MediaBinder) iBinder;
            mService = mediaBinder.getMediaService();
            update();
            initSettingService();
            initStatePlaying();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initToolbar();
        initComponents();
        initListeners();
        receiveStateMedia();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
        update();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeCallbacks();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mMediaConnection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_shuffle:
                onShuffleMusicClick();
                break;
            case R.id.image_previous:
                mService.previous();
                break;
            case R.id.image_state:
                if (mService.isPlay()) {
                    mService.pause();
                } else {
                    mService.resume();
                }
                break;
            case R.id.image_next:
                mService.next();
                break;
            case R.id.image_repeat:
                repeatMode();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        mTextCurrentDuration = findViewById(R.id.text_current_duration);
        mTextDuration = findViewById(R.id.text_duration);
        mTextTitleSong = findViewById(R.id.text_title_playing);
        mTextTitleSong.setSelected(true);
        mSeekBar = findViewById(R.id.seek_bar);
        mButtonShuffle = findViewById(R.id.image_shuffle);
        mButtonPrevious = findViewById(R.id.image_previous);
        mButtonState = findViewById(R.id.image_state);
        mButtonNext = findViewById(R.id.image_next);
        mButtonRepeat = findViewById(R.id.image_repeat);
        mViewPager = findViewById(R.id.pager_playing);
        mViewPager.setAdapter(new PlayingPagerAdapter(getSupportFragmentManager()));
        mHandler = new Handler();
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mMediaConnection, BIND_AUTO_CREATE);
    }

    private void initListeners() {
        mButtonShuffle.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
        mButtonState.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonRepeat.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekChange);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_play_music);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_toolbar_playmusic));
    }

    private String convertToTime(long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        String time = sdf.format(duration);
        return time;
    }

    private void update() {
        mHandler.postDelayed(mTimeCounter, DEFAULT_DELAY);
    }

    private void repeatMode() {
        int repeatMode = mService.getRepeat();
        switch (repeatMode) {
            case RepeatType.NO_REPEAT:
                mService.setRepeat(RepeatType.REPEAT_ONE);
                mButtonRepeat.setImageResource(R.drawable.ic_repeat_one_on);
                break;
            case RepeatType.REPEAT_ONE:
                mService.setRepeat(RepeatType.REPEAT_ALL);
                mButtonRepeat.setImageResource(R.drawable.ic_repeat_on);
                break;
            case RepeatType.REPEAT_ALL:
                mService.setRepeat(RepeatType.NO_REPEAT);
                mButtonRepeat.setImageResource(R.drawable.ic_repeat_off);
                break;
        }
    }

    private void onShuffleMusicClick() {
        if (mService.isShuff()) {
            mButtonShuffle.setImageResource(R.drawable.ic_shuffle_off);
            mService.setShuff(false);
            mService.unShuffleSong();
        } else {
            mButtonShuffle.setImageResource(R.drawable.ic_shuffle_on);
            mService.setShuff(true);
            mService.shuffleSong();
        }
    }

    private void initStatePlaying() {
        if (mService.isPlay()) {
            mButtonState.setImageResource(R.drawable.ic_pause);
            startAnimationImagePlaying();
        } else {
            mButtonState.setImageResource(R.drawable.ic_play);
            clearAnimationImagePlaying();
        }
    }

    private void initSettingService() {
        if (mService.isShuff()) {
            mButtonShuffle.setImageResource(R.drawable.ic_shuffle_on);
        } else {
            mButtonShuffle.setImageResource(R.drawable.ic_shuffle_off);
        }
        int repeatMode = mService.getRepeat();
        switch (repeatMode) {
            case RepeatType.NO_REPEAT:
                mButtonRepeat.setImageResource(R.drawable.ic_repeat_off);
                break;
            case RepeatType.REPEAT_ONE:
                mButtonRepeat.setImageResource(R.drawable.ic_repeat_one_on);
                break;
            case RepeatType.REPEAT_ALL:
                mButtonRepeat.setImageResource(R.drawable.ic_repeat_on);
                break;
        }
    }

    private Runnable mTimeCounter = new Runnable() {
        @Override
        public void run() {
            if (!mTextTitleSong.getText().toString().equals(mService.getTitleSongPlaying())) {
                mTextTitleSong.setText(mService.getTitleSongPlaying());
                mTextDuration.setText(convertToTime(mService.getDuration()));
                mTextCurrentDuration.setText(TIME_DEFAULT);
            }
            if (mService.isPlay()) {
                long currentPercent = 100 * mService.getCurrentDuration() / mService.getDuration();
                mSeekBar.setProgress((int) currentPercent);
                mTextCurrentDuration.setText(convertToTime(mService.getCurrentDuration()));
            }
            update();
        }
    };

    private void receiveStateMedia() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ACTION_STATE_MEDIA);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(ACTION_STATE_MEDIA)) {
                    if (intent.getBooleanExtra(Constants.ConstantBroadcast.EXTRA_STATE_MEDIA,
                            false)) {
                        mButtonState.setImageResource(R.drawable.ic_pause);
                        startAnimationImagePlaying();
                    } else {
                        mButtonState.setImageResource(R.drawable.ic_play);
                        clearAnimationImagePlaying();
                    }
                }
            }
        };
    }

    private void startAnimationImagePlaying() {
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_image_playing);
       // mImagePlaying.startAnimation(mAnimation);
    }

    private void clearAnimationImagePlaying() {
       // mImagePlaying.clearAnimation();
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int percent = seekBar.getProgress();
            int currentSeekBarTo = percent * (int) mService.getDuration() / 100;
            mService.seekTo(currentSeekBarTo);
        }
    };

    private void removeCallbacks() {
        mHandler.removeCallbacks(mTimeCounter);
    }
}
