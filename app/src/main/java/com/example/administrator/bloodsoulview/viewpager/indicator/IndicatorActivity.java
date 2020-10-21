package com.example.administrator.bloodsoulview.viewpager.indicator;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.bloodsoulview.R;
import com.example.administrator.bloodsoulview.viewpager.pager.TestFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 很精彩，应有尽有 ： https://github.com/hackware1993/MagicIndicator
 */
public class IndicatorActivity extends AppCompatActivity {

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private ArrayList<String> mDataList = new ArrayList<>();
    private CommonNavigator mCommonNavigator;
    private FragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator2);
        ButterKnife.bind(this);
        initIndicator();
        initViewPager();
        initData();
    }

    private void initIndicator() {
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);

                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);

                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;    // 没有指示器，因为title的指示作用已经很明显了
            }
        });

        mMagicIndicator.setNavigator(mCommonNavigator);
    }

    private void initViewPager() {
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return new TestFragment();
            }

            @Override
            public int getCount() {
                return mDataList.size();
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mMagicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mMagicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mMagicIndicator.onPageScrollStateChanged(state);
            }
        });

        mViewPager.setCurrentItem(1);
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            mDataList.add("sos" + i);
        }
        mCommonNavigator.notifyDataSetChanged();
        mPagerAdapter.notifyDataSetChanged();
    }
}
