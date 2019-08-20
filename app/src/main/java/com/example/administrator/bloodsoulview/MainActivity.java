package com.example.administrator.bloodsoulview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.administrator.bloodsoulview.marquee.MarqueeText;
import com.example.administrator.bloodsoulview.marquee.MarqueeText2;
import com.example.administrator.bloodsoulview.view.LinearGradientTextView;
import com.example.administrator.bloodsoulview.view.ScrollPageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.linear_gradient_textview)
    LinearGradientTextView mLinearGradientTextview;
    @BindView(R.id.scroll_page_view)
    ScrollPageView mScrollPageView;
    @BindView(R.id.marquee_title)
    TextView mMarqueeTitle;
    @BindView(R.id.custom_marquee_title)
    MarqueeText mCustomMarqueeTitle;
    @BindView(R.id.custom_marquee_title2)
    MarqueeText2 mCustomMarqueeTitle2;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView mLottieAnimationView;
    @BindView(R.id.guide)
    LottieAnimationView mGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLinearGradientTextview.post(new Runnable() {
            @Override
            public void run() {
                mLinearGradientTextview.startShow();
            }
        });

        mScrollPageView.startRoll();

        mMarqueeTitle.setSelected(true);
        mCustomMarqueeTitle.setSelected(true);

        mCustomMarqueeTitle2.startScroll();

        lottie();
    }

    private void lottie() {
//        mLottieAnimationView.setImageAssetsFolder("images");
//        mLottieAnimationView.setAnimation("newnewsmusical.json");
//        mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        mLottieAnimationView.playAnimation();

        mGuide.setImageAssetsFolder("images");
        mGuide.setAnimation("mg.json");
        mGuide.setRepeatCount(LottieDrawable.INFINITE);
        mGuide.playAnimation();
    }

    public void clickBtn1(View view) {
        startActivity(new Intent(this, FlowActivity.class));
    }

    public void clickBtn2(View view) {
        startActivity(new Intent(this, FlowRecyclerViewActivity.class));
    }

    public void clickBtn3(View view) {
        startActivity(new Intent(this, FlowCustomActivity.class));
    }

    public void clickBtn4(View view) {
        startActivity(new Intent(this, OutTouchActivity.class));
    }
}
