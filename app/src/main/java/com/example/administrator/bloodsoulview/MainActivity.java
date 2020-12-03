package com.example.administrator.bloodsoulview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.blankj.utilcode.util.SizeUtils;
import com.example.administrator.bloodsoulview.bigimg.BigImgActivity;
import com.example.administrator.bloodsoulview.blur.ImageViewBlurActivity;
import com.example.administrator.bloodsoulview.clickspan.ClickSpanActivity;
import com.example.administrator.bloodsoulview.dialog.RcProgressDialog;
import com.example.administrator.bloodsoulview.indicator.IndicatorActivity;
import com.example.administrator.bloodsoulview.linear_color.LinearColorActivity;
import com.example.administrator.bloodsoulview.loopviewpager.LoopViewPagerActivity;
import com.example.administrator.bloodsoulview.lottie.LottieActivity;
import com.example.administrator.bloodsoulview.marquee.MarqueeText;
import com.example.administrator.bloodsoulview.marquee.MarqueeText2;
import com.example.administrator.bloodsoulview.optionmenu.MenuOptionActivity;
import com.example.administrator.bloodsoulview.record_song.RecordSongActivity;
import com.example.administrator.bloodsoulview.shadow.ShadowDrawable;
import com.example.administrator.bloodsoulview.svga.SvgaActivity;
import com.example.administrator.bloodsoulview.ticker.TickerActivity;
import com.example.administrator.bloodsoulview.turntable.TurntableActivity;
import com.example.administrator.bloodsoulview.view.LinearGradientTextView;
import com.example.administrator.bloodsoulview.view.ScrollPageView;
import com.example.administrator.bloodsoulview.viewpager.ViewPagerActivity;
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

        // inclusive exclusive 没有插入其他数据时，都相当于 [start, end)
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("0123456");
        ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 1, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        int length = ssb.length();
        ssb.append("789");
        ssb.setSpan(new ForegroundColorSpan(Color.BLUE), length, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 测试插入其他数据
        // 证实，
        // 1. 插入start和end中间，则直接同步颜色
        // 1. 插入start位置，inclusive同步，exclusive不同步
        ssb.insert(3, "s");

        mColorText.setText(ssb);
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

    public void clickBtn16(View view) {
        startActivity(new Intent(this, LinearColorActivity.class));
    }

    public void clickBtn17(View view) {
        startActivity(new Intent(this, ViewPagerActivity.class));
    }

    public void clickBtn18(View view) {
        startActivity(new Intent(this, TurntableActivity.class));
    }

    public void clickBtn19(View view) {
        startActivity(new Intent(this, LottieActivity.class));
    }

    public void clickBtn20(View view) {
        startActivity(new Intent(this, ClickSpanActivity.class));
    }

    public void clickBtn21(View view) {
        startActivity(new Intent(this, ParticleActivity.class));
    }

    public void clickBtn22(View view) {
        startActivity(new Intent(this, TransitionActivity.class));
    }

    public void clickBtn23(View view) {
        startActivity(new Intent(this, BigImgActivity.class));
    }

    public void clickBtn24(View view) {
        startActivity(new Intent(this, DesignActivity.class));
    }

    public void clickBtn25(View view) {
        startActivity(new Intent(this, SvgaActivity.class));
    }

    public void clickBtn26(View view) {
        startActivity(new Intent(this, FlipboardActivity.class));
    }
}
