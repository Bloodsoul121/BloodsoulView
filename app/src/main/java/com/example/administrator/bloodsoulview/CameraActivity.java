package com.example.administrator.bloodsoulview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.bloodsoulview.view.CameraView;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mCameraView = findViewById(R.id.camera_view);
        mCameraView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        ViewGroup.LayoutParams layoutParams = mCameraView.getLayoutParams();
//        layoutParams.width = 400;
//        layoutParams.height = 400;
//        mCameraView.setLayoutParams(layoutParams);
    }
}