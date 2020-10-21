package com.example.administrator.bloodsoulview.record_song;

import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordSongActivity extends AppCompatActivity {

    @BindView(R.id.lav_out)
    LottieAnimationView mLavOut;
    @BindView(R.id.lav_int)
    LottieAnimationView mLavInt;
    @BindView(R.id.progress_ball)
    ProgressBall mProgressBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_song);
        ButterKnife.bind(this);

        mProgressBall.setMaxProgress(1);
    }

    public void clickBtn1(View view) {
        mLavOut.setAnimation("whiteyuandian.json");
//        mLavOut.setRepeatCount(LottieDrawable.INFINITE);
//        mLavOut.playAnimation();
        mLavOut.setRepeatCount(0);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mLavOut.setProgress(value);

                mProgressBall.setProgress(value);
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();


        mLavInt.setImageAssetsFolder("image5");
        mLavInt.setAnimation("mike.json");
        mLavInt.setRepeatCount(1);
        mLavInt.playAnimation();
    }
}
