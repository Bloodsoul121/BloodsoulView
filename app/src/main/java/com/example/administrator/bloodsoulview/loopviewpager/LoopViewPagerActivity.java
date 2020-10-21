package com.example.administrator.bloodsoulview.loopviewpager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.bloodsoulview.R;
import com.example.administrator.bloodsoulview.indicator.CircleIndicator;

public class LoopViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_view_pager);
        init();
    }

    private void init() {
        LoopViewPager viewpager = findViewById(R.id.viewpager);
        CircleIndicator indicator = findViewById(R.id.indicator);
        viewpager.setAdapter(new SamplePagerAdapter());
        indicator.setViewPager(viewpager);
    }
}
