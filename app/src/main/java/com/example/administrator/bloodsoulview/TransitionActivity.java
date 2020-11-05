package com.example.administrator.bloodsoulview;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransitionActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView mIv;
    @BindView(R.id.root)
    LinearLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);
    }

    public void clickBtn1(View view) {
        Fade fade = new Fade();
        Slide slide = new Slide();
        TransitionSet set = new TransitionSet();
        set.addTransition(fade).addTransition(slide).setOrdering(TransitionSet.ORDERING_TOGETHER);
        TransitionManager.beginDelayedTransition(mRootView, set);
        toggleViewVisible(mIv);
    }

    private void toggleViewVisible(View... views) {
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.INVISIBLE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
    }
}
