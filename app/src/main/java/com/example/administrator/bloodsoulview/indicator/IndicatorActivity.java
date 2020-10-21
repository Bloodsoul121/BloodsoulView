package com.example.administrator.bloodsoulview.indicator;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndicatorActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mViewpager.setAdapter(new PagerAdapter() {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TextView view = new TextView(IndicatorActivity.this);
                view.setText("hello");
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
        });

        mIndicator.setViewPager(mViewpager);
    }
}
