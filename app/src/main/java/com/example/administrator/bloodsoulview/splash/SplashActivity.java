package com.example.administrator.bloodsoulview.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.example.administrator.bloodsoulview.MainActivity;
import com.example.administrator.bloodsoulview.R;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        BarUtils.setStatusBarColor(this, Color.parseColor("#ffffff"));

        // 适配刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Debug.startMethodTracing("tag1");
//        a();
//        Debug.stopMethodTracing();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            }
//        }, 1000);

//        startActivity(new Intent(SplashActivity.this, Main2Activity.class));

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    }

    private void a() {
        try {
            Thread.sleep(1000);
            b();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void b() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
