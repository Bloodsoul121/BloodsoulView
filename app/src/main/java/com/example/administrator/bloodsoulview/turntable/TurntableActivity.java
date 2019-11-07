package com.example.administrator.bloodsoulview.turntable;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.administrator.bloodsoulview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TurntableActivity extends AppCompatActivity {

    @BindView(R.id.turn_table_view)
    TurnTableShowView mTurnTableShowView;
    @BindView(R.id.turn_table_fold_view)
    LottieAnimationView mTurnTableFoldView;
    @BindView(R.id.test_view)
    LottieAnimationView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truntable);
        ButterKnife.bind(this);
    }

    public void clickBtn1(View view) {
        mTurnTableShowView.receive();
    }

    public void clickBtn2(View view) {
        mTurnTableShowView.forceStop();
    }

    public void clickBtn3(View view) {
        if (mTurnTableShowView.getVisibility() == View.VISIBLE) {
            mTurnTableShowView.setVisibility(View.GONE);
        } else {
            mTurnTableShowView.setVisibility(View.VISIBLE);
        }
    }

    public void clickBtn4(View view) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(String.valueOf(i + 1));
        }
        mTurnTableShowView.configNums(list);
    }

    public void clickBtn5(View view) {
        List<TurnTableShowView.GestureIcon> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            TurnTableShowView.GestureIcon gestureIcon = new TurnTableShowView.GestureIcon();
            if (i % 3 == 0) {
                gestureIcon.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_bu);
                gestureIcon.result = "布";
            } else if (i % 3 == 1) {
                gestureIcon.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_shitou);
                gestureIcon.result = "石头";
            } else {
                gestureIcon.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_jiandao);
                gestureIcon.result = "剪刀";
            }
            list.add(gestureIcon);
        }
        mTurnTableShowView.configIcons(list);
    }

    public void clickBtn6(View view) {
        if (mTurnTableFoldView.isAnimating()) {
            mTurnTableFoldView.cancelAnimation();
        } else {
            mTurnTableFoldView.playAnimation();
        }
    }

    @OnClick({R.id.test_view, R.id.turn_table_fold_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_view:
                mTestView.setMinAndMaxProgress(0.5f, 1f);
                mTestView.playAnimation();
                break;
            case R.id.turn_table_fold_view:
                break;
        }
    }
}
