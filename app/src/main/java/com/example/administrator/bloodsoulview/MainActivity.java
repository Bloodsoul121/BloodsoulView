package com.example.administrator.bloodsoulview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.bloodsoulview.view.LinearGradientTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.linear_gradient_textview)
    LinearGradientTextView mLinearGradientTextview;

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
