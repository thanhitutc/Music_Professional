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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants;
import com.example.framgianguyenvanthanhd.music_professional.Utils.KeysPref;
import com.example.framgianguyenvanthanhd.music_professional.Utils.SharedPrefs;
import com.example.framgianguyenvanthanhd.music_professional.data.comment.Comment;
import com.example.framgianguyenvanthanhd.music_professional.data.model.Setting;
import com.example.framgianguyenvanthanhd.music_professional.data.model.SongPlaying;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.CommentRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.PersonalLikeRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SettingRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SongParameterRepository;
import com.example.framgianguyenvanthanhd.music_professional.data.resources.local.SettingLocalDataSource;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment.CommentAdapter;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment.PlayingMusicContract;
import com.example.framgianguyenvanthanhd.music_professional.screens.playmusic.comment.PlayingMusicPresenter;
import com.example.framgianguyenvanthanhd.music_professional.service.MediaService;
import com.example.framgianguyenvanthanhd.music_professional.service.RepeatType;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static com.example.framgianguyenvanthanhd.music_professional.Utils.Constants.ConstantBroadcast.ACTION_STATE_MEDIA;


/**
 * Created by MyPC on 31/01/2018.
 */

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener, PlayingMusicContract.PlayingView {
    private static final int DEFAULT_DELAY = 500;
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

    private OnChangeSongListener mOnChangeSongListener;
    private OnChangeSongListener.OnUpdateImageSong mOnUpDateImageSongListener;

    private OnStatePlayingListener mOnStatePlayingListener;

    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mBtnCloseComment;
    private TextView mTxtComment;

    private PlayingMusicContract.PlayingPresenter mPlayingPresenter;
    private CommentAdapter mCommentAdapter;
    private RecyclerView mRecyclerViewComment;
    private ProgressBar mProgressBarComment;
    private EditText edtComment;
    private CheckBox cboLike;

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
            initStatePlaying();
            initLikeSong();
            mService.getmPresenter().getSongsPlaying();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initService();
        initToolbar();
        initComponents();
        initSettingService();
        initListeners();
        receiveStateMedia();
        initCommentBottomSheet();
    }

    private void initCommentBottomSheet() {
        edtComment = findViewById(R.id.edt_content);
        View bottomSheetComment = findViewById(R.id.layout_comment);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetComment);

        mBtnCloseComment = findViewById(R.id.btn_close);
        mTxtComment = findViewById(R.id.txt_comment);
        mBtnCloseComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                View v = getCurrentFocus();
                if (view != null){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        bottomSheetComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mBtnCloseComment.setVisibility(View.INVISIBLE);
                        mTxtComment.setVisibility(View.VISIBLE);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        mBtnCloseComment.setVisibility(View.VISIBLE);
                        mTxtComment.setVisibility(View.INVISIBLE);
                        findViewById(R.id.txt_no_comment).setVisibility(View.INVISIBLE);
                        mPlayingPresenter.fetchComment(mService.getIdSongPlaying());
                        mProgressBarComment.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mRecyclerViewComment = findViewById(R.id.rv_comment);
        mProgressBarComment = findViewById(R.id.progress_bar_comment);
        mRecyclerViewComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mPlayingPresenter = new PlayingMusicPresenter(
                CommentRepository.getInstance(),
                PersonalLikeRepository.getInstance(),
                SongParameterRepository.getInstance(),
                this);
        mPlayingPresenter.setView(this);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = SharedPrefs.getInstance().get(KeysPref.USER_NAME.name(), String.class);
                if (username == null || username.isEmpty()){
                    Toasty.warning(getBaseContext(), getResources().getString(R.string.txt_login_to_comment), Toast.LENGTH_SHORT, true).show();
                    return;
                }

                if (edtComment.getText().toString().isEmpty()) {
                    return;
                }
                findViewById(R.id.txt_no_comment).setVisibility(View.INVISIBLE);
                mPlayingPresenter.postComment(mService.getIdSongPlaying(), edtComment.getText().toString());
                String avatar = SharedPrefs.getInstance().get(KeysPref.AVATAR.name(), String.class);
                String firstName = SharedPrefs.getInstance().get(KeysPref.FIRST_NAME.name(), String.class);
                String lastName = SharedPrefs.getInstance().get(KeysPref.LAST_NAME.name(), String.class);
                if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
                    username = firstName + lastName;
                }
                Comment comment = new Comment(
                        avatar,
                        username,
                        firstName,
                        lastName,
                        edtComment.getText().toString(),
                        getResources().getString(R.string.txt_posting));
                mCommentAdapter.postComment(comment);
                edtComment.setText("");
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtComment.getWindowToken(), 0);
            }
        });

    }

    private void initLikeSong() {
        cboLike = findViewById(R.id.cbo_like);
        mPlayingPresenter.checkLikeSong(mService.getIdSongPlaying());

        cboLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cboLike.isChecked()) {
                    mPlayingPresenter.updateLikeSong(mService.getIdSongPlaying(), false);
                }
                if (cboLike.isChecked()){
                    mPlayingPresenter.updateLikeSong(mService.getIdSongPlaying(), true);
                }
            }
        });
    }

    @Override
    public void postCommentSuccess() {
        mCommentAdapter.updatePostSuccess(getResources().getString(R.string.txt_just_now));
    }

    @Override
    public void postCommentError() {
        Toasty.error(this, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void fetchCommentSuccess(@NotNull List<Comment> comments) {
        mProgressBarComment.setVisibility(View.INVISIBLE);
        if (comments.size() == 0) {
            findViewById(R.id.txt_no_comment).setVisibility(View.VISIBLE);
        }
        mCommentAdapter = new CommentAdapter(comments);
        mRecyclerViewComment.setAdapter(mCommentAdapter);
    }

    @Override
    public void fetchCommentFail() {
        mProgressBarComment.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(PlayingMusicContract.PlayingPresenter presenter) {

    }

    @Override
    public void checkLikeSuccess() {
        if (!cboLike.isChecked()){
            cboLike.setChecked(true);
        }
    }

    @Override
    public void checkLikeFailure() {
        cboLike.setChecked(false);
    }

    @Override
    public void updateLikeSongSuccess() {
    }

    @Override
    public void updateLikeSongFail() {
        Toasty.error(this, getString(R.string.txt_error), Toast.LENGTH_SHORT, true).show();
        if (cboLike.isChecked()) {
            cboLike.setChecked(false);
        } else {
            cboLike.setChecked(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
        initService();
        update();
    }

    private void initService(){
        Intent intent = new Intent(this, MediaService.class);
        bindService(intent, mMediaConnection, BIND_AUTO_CREATE);
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_24dp);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mService.isPlay()) {
                    mButtonState.setImageResource(R.drawable.ic_pause);
                    if (mOnStatePlayingListener != null) {
                        mOnStatePlayingListener.onSongPlaying();
                    }
                } else {
                    mButtonState.setImageResource(R.drawable.ic_play);
                    if (mOnStatePlayingListener != null) {
                        mOnStatePlayingListener.onSongPause();
                    }
                }
            }
        }, 1000);
    }

    private void initSettingService() {
        Setting setting = SettingRepository.getInstance(SettingLocalDataSource.getInstance(this)).getSetting();
        if (setting.isShuffleMode()) {
            mButtonShuffle.setImageResource(R.drawable.ic_shuffle_on);
        } else {
            mButtonShuffle.setImageResource(R.drawable.ic_shuffle_off);
        }
        int repeatMode = setting.getRepeatMode();
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
                mOnChangeSongListener.onUpdateSong(mService.getSongPlaying());
                mOnUpDateImageSongListener.onUpdateImageSong(mService.getSongPlaying());
                mPlayingPresenter.checkLikeSong(mService.getIdSongPlaying());
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
                        mOnStatePlayingListener.onSongPlaying();
                    } else {
                        mButtonState.setImageResource(R.drawable.ic_play);
                        mOnStatePlayingListener.onSongPause();
                    }
                }
            }
        };
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

    public List<SongPlaying> getSongsPlaying() {
        return mService.getSongsPlaying();
    }

    public void setOnChangeSongListener(OnChangeSongListener listener) {
        mOnChangeSongListener = listener;
    }

    public void setOnStatePlayingListener(OnStatePlayingListener onStatePlayingListener) {
        mOnStatePlayingListener = onStatePlayingListener;
    }

    public void setmOnUpDateImageSongListener(OnChangeSongListener.OnUpdateImageSong mOnUpDateImageSongListener) {
        this.mOnUpDateImageSongListener = mOnUpDateImageSongListener;
    }

    public MediaService getService() {
        return mService;
    }

    public void playWithSong(SongPlaying songPlaying) {
        mService.playWithSongClick(songPlaying);
    }

}
