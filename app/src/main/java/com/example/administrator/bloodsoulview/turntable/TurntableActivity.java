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

public class TurntableActivity extends AppCompatActivity {

    @BindView(R.id.turn_table_view)
    TurntableView mTurntableView;
    @BindView(R.id.turn_table_fold_view)
    LottieAnimationView mTurnTableFoldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truntable);
        ButterKnife.bind(this);
    }

    public void clickBtn1(View view) {
        mTurntableView.receive();
    }

    public void clickBtn2(View view) {
        mTurntableView.forceStop();
    }

    public void clickBtn3(View view) {
        if (mTurntableView.getVisibility() == View.VISIBLE) {
            mTurntableView.setVisibility(View.GONE);
        } else {
            mTurntableView.setVisibility(View.VISIBLE);
        }
    }

    public void clickBtn4(View view) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(String.valueOf(i + 1));
        }
        mTurntableView.configNums(list);
    }

    public void clickBtn5(View view) {
        List<TurntableView.GestureIcon> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            TurntableView.GestureIcon gestureIcon = new TurntableView.GestureIcon();
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
        mTurntableView.configIcons(list);
    }

    public void clickBtn6(View view) {
        if (mTurnTableFoldView.isAnimating()) {
            mTurnTableFoldView.cancelAnimation();
        } else {
            mTurnTableFoldView.playAnimation();
        }
    }
}
