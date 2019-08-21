package com.example.administrator.bloodsoulview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerTransformActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private PagerAdapter mAdapter;

    private int[] imgRes = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_5, R.drawable.img_6, R.drawable.img_9};

    private static final float MIN_ALPHA = 0.2f;
    private static final float MIN_SCALE = 0.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_transform);
        ButterKnife.bind(this);

        //设置Page间间距
        mViewPager.setPageMargin(20);
        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter() {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView view = new ImageView(ViewPagerTransformActivity.this);
                view.setImageResource(imgRes[position]);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
        });

        mViewPager.setPageTransformer(false, mPageTransformer1);
    }

    private ViewPager.PageTransformer mPageTransformer2 = new ViewPager.PageTransformer() {
        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position < -1) {
                // [-2,-1]
                view.setAlpha(2 + position);
            } else if (position <= 1) {
                // [-1,1]
                if (position < 0) {
                    //[-1，0]
                    float alpha = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);
                    view.setAlpha(alpha);
                    float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                } else {
                    //[1，0]
                    float alpha = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - position);
                    view.setAlpha(alpha);
                    float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            } else {
                // (1,+Infinity]
                view.setAlpha(MIN_ALPHA);
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            }
        }
    };

    private ViewPager.PageTransformer mPageTransformer1 = new ViewPager.PageTransformer() {
        @Override
        public void transformPage(@NonNull View view, float position) {

            System.out.println("PageTransformer -- " + position);

            if (position < -1) {
                view.setAlpha(MIN_ALPHA);
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) {
                // [-1,1]
                if (position < 0) {
                    //[-1，0]
                    float alpha = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);
                    view.setAlpha(alpha);
                    float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                } else {
                    //[1，0]
                    float alpha = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - position);
                    view.setAlpha(alpha);
                    float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            } else {
                // (1,+Infinity]
                view.setAlpha(MIN_ALPHA);
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            }
        }
    };

}
