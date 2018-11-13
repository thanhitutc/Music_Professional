package com.example.framgianguyenvanthanhd.music_professional.screens.offline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.framgianguyenvanthanhd.music_professional.R;
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants;


/**
 * main
 **/
public class MainOfflineActivity extends AppCompatActivity {
    public static final int PAGE_LIMIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_offline);
        initToolbar();
        initComponents();
    }


    public static Intent getInstance(Context context, int mainType) {
        Intent intent = new Intent(context, MainOfflineActivity.class);
        intent.putExtra(Constants.ConstantIntent.MAIN_TYPE, mainType);
        return intent;
    }

    private void createSendEmailToDeveloper() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"thanhitutc@gmail.com"});
        intent.putExtra(Intent.EXTRA_PHONE_NUMBER, new String[] { "+84" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback music application");
        startActivity(intent);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle(getBaseContext().getResources().getString(R.string.app_name));
    }

    private void initComponents() {
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new MainOfflinePagerAdapter(getBaseContext(), getSupportFragmentManager()));
        pager.setOffscreenPageLimit(PAGE_LIMIT);
        pager.setCurrentItem(getIntent().getIntExtra(Constants.ConstantIntent.MAIN_TYPE,0));
    }
}

