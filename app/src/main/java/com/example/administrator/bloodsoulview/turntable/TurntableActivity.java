package com.example.administrator.bloodsoulview.turntable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.bloodsoulview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TurntableActivity extends AppCompatActivity {

    @BindView(R.id.turn_table_view)
    TurntableView mTurntableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truntable);
        ButterKnife.bind(this);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(String.valueOf(i + 1));
        }
        mTurntableView.config(list);
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
}
