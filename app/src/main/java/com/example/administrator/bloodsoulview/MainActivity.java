package com.example.administrator.bloodsoulview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.blankj.utilcode.util.SizeUtils;
import com.example.administrator.bloodsoulview.blur.ImageViewBlurActivity;
import com.example.administrator.bloodsoulview.dialog.RcProgressDialog;
import com.example.administrator.bloodsoulview.indicator.IndicatorActivity;
import com.example.administrator.bloodsoulview.loopviewpager.LoopViewPagerActivity;
import com.example.administrator.bloodsoulview.marquee.MarqueeText;
import com.example.administrator.bloodsoulview.marquee.MarqueeText2;
import com.example.administrator.bloodsoulview.optionmenu.MenuOptionActivity;
import com.example.administrator.bloodsoulview.record_song.RecordSongActivity;
import com.example.administrator.bloodsoulview.shadow.ShadowDrawable;
import com.example.administrator.bloodsoulview.ticker.TickerActivity;
import com.example.administrator.bloodsoulview.view.LinearGradientTextView;
import com.example.administrator.bloodsoulview.view.ScrollPageView;
import com.example.administrator.bloodsoulview.voiceprogress.VoiceProgress;
import com.example.administrator.bloodsoulview.voiceprogress.VoiceProgressActivity;
import com.example.administrator.bloodsoulview.zhongdong.MiddleEastActivity;

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
    @BindView(R.id.like)
    LottieAnimationView mLike;
    @BindView(R.id.color_text)
    TextView mColorText;
    @BindView(R.id.shadow_text)
    TextView mShadowText;

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

        setColorText();

        ShadowDrawable.setShadowDrawable(mShadowText, Color.parseColor("#2979FF"), SizeUtils.dp2px(8),
                Color.parseColor("#992979FF"), SizeUtils.dp2px(6), 0, 0);
    }

    private void setColorText() {
        // 不能用同一个 Span
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);

        SpannableStringBuilder style = new SpannableStringBuilder();
        style.append("abcdefghi");
        style.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int length = style.length();
        style.append("jklmnopqrstuvwxyz");
        style.setSpan(new ForegroundColorSpan(Color.BLUE), length, style.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mColorText.setText(style);
    }

    private void lottie() {
        mLottieAnimationView.setImageAssetsFolder("images3");
        mLottieAnimationView.setAnimation("guangpan.json");
        mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        mLottieAnimationView.playAnimation();

        mGuide.setImageAssetsFolder("images");
        mGuide.setAnimation("mg.json");
        mGuide.setRepeatCount(LottieDrawable.INFINITE);
//        mGuide.setRepeatCount(0);
//        mGuide.playAnimation();

        mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLike.playAnimation();
            }
        });
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

    public void clickBtn5(View view) {
        startActivity(new Intent(this, ViewPagerTransformActivity.class));
    }

    public void clickLottieBtnPlay(View view) {
        mGuide.playAnimation();
        System.out.println("mGuide isAnimating " + mGuide.isAnimating());
    }

    public void clickLottieBtnResume(View view) {
        mGuide.resumeAnimation();
        System.out.println("mGuide isAnimating " + mGuide.isAnimating());
    }

    public void clickLottieBtnPause(View view) {
        mGuide.pauseAnimation();
        System.out.println("mGuide isAnimating " + mGuide.isAnimating());
    }

    public void clickLottieBtnCancel(View view) {
        mGuide.cancelAnimation();
        System.out.println("mGuide isAnimating " + mGuide.isAnimating());
    }

    public void clickBtn6(View view) {
        startActivity(new Intent(this, FontActivity.class));
    }

    public void clickBtn7(View view) {
        RcProgressDialog.showProgressDialog(this);
    }

    public void clickBtn8(View view) {
        startActivity(new Intent(this, IndicatorActivity.class));
    }

    public void clickBtn9(View view) {
        startActivity(new Intent(this, ImageViewBlurActivity.class));
    }

    public void clickBtn10(View view) {
        startActivity(new Intent(this, LoopViewPagerActivity.class));
    }

    public void clickBtn11(View view) {
        startActivity(new Intent(this, MenuOptionActivity.class));
    }

    public void clickBtn12(View view) {
        startActivity(new Intent(this, TickerActivity.class));
    }

    public void clickBtn13(View view) {
        startActivity(new Intent(this, MiddleEastActivity.class));
    }

    public void clickBtn14(View view) {
        startActivity(new Intent(this, RecordSongActivity.class));
    }

    public void clickBtn15(View view) {
        startActivity(new Intent(this, VoiceProgressActivity.class));
    }
}
