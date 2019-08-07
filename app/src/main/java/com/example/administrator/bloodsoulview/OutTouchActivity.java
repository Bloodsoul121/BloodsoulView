package com.example.administrator.bloodsoulview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bloodsoulview.view.OutTouchViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutTouchActivity extends AppCompatActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.container)
    OutTouchViewGroup mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_touch);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mContainer.setOnTouchOutsideViewListener(new OutTouchViewGroup.OnTouchOutsideViewListener() {
            @Override
            public void onTouchOutside(MotionEvent event) {
                Toast.makeText(OutTouchActivity.this, "onTouchOutside", Toast.LENGTH_SHORT).show();
            }
        });

        mContainer.addOnTouchOutsideView(mTv1);
        mContainer.addOnTouchOutsideView(mTv2);
        mContainer.addOnTouchOutsideView(mTv3);
    }
}
