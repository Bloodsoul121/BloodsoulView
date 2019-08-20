package com.example.administrator.bloodsoulview.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.bloodsoulview.R;

public class ScrollPageView extends LinearLayout {

    private TextView mCharmTitle;
    private TextView mCharmName;
    private ImageView mFirstImg;
    private ImageView mSecondImg;
    private ImageView mThirdImg;
    private AnimationSet mAnim1In;
    private AnimationSet mAnim2In;
    private AnimationSet mAnim3In;
    private Animation mAnim1Update;
    private Animation mAnim2Update;
    private Animation mAnim3Update;
    private boolean mStopAnimation;
    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mStopAnimation) {
                startRoll();
            }
        }
    };

    public ScrollPageView(Context context) {
        this(context, null);
    }

    public ScrollPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_view_scroll_page2, this, true);
        mCharmTitle = findViewById(R.id.charm_title);
        mCharmName = findViewById(R.id.charm_name);
        mFirstImg = findViewById(R.id.rank_first);
        mSecondImg = findViewById(R.id.rank_second);
        mThirdImg = findViewById(R.id.rank_third);

//        mFirstImg.addBorder(2f, 0xffffffff);
//        mSecondImg.addBorder(2f, 0xffffffff);
//        mThirdImg.addBorder(2f, 0xffffffff);

        mAnim1In = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.charm_rank_first);
        mAnim1In.setAnimationListener(new OutAnimation());
        mAnim1Update = mAnim1In.getAnimations().get(0);
        mAnim1Update.setAnimationListener(new OutAnimation());
        mAnim2In = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.charm_rank_second);
        mAnim2Update = mAnim2In.getAnimations().get(0);
        mAnim2Update.setAnimationListener(new OutAnimation());
        mAnim3In = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.charm_rank_third);
        mAnim3Update = mAnim3In.getAnimations().get(0);
        mAnim3Update.setAnimationListener(new OutAnimation());
    }

    public void updateUI() {
        updateTitle();
        updateFirstImg();
        updateSecondImg();
        updateThirdImg();
    }

    private void updateTitle() {

    }

    private void updateFirstImg() {
        if (mFirstImg != null) {
            mFirstImg.setImageResource(R.drawable.arena_popularity_icon);
        }
    }

    private void updateSecondImg() {
        if (mSecondImg != null) {
            mSecondImg.setImageResource(R.drawable.arena_popularity_icon);
        }
    }

    private void updateThirdImg() {
        if (mThirdImg != null) {
            mThirdImg.setImageResource(R.drawable.arena_popularity_icon);
        }
    }

    public void startRoll() {
        mStopAnimation = false;
        if (mFirstImg != null) {
            mFirstImg.startAnimation(mAnim1In);
        }
        if (mSecondImg != null) {
            mSecondImg.startAnimation(mAnim2In);
        }
        if (mThirdImg != null) {
            mThirdImg.startAnimation(mAnim3In);
        }
    }

    public void stopRoll() {
        mStopAnimation = true;
        cleanAnimation();
    }

    private void cleanAnimation() {
        if (mFirstImg != null) {
            mFirstImg.clearAnimation();
        }
        if (mSecondImg != null) {
            mSecondImg.clearAnimation();
        }
        if (mThirdImg != null) {
            mThirdImg.clearAnimation();
        }
    }

    public class OutAnimation implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == mAnim1In) {
                mHandler.postDelayed(mRunnable, 3000);
            } else if (animation == mAnim1Update) {
                updateFirstImg();
            } else if (animation == mAnim2Update) {
                updateSecondImg();
            } else if (animation == mAnim3Update) {
                updateThirdImg();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

}
