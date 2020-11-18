package com.example.administrator.bloodsoulview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;

public class DesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.transparent));
//        BarUtils.transparentStatusBar(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("HD TEST");
        setSupportActionBar(toolbar);
    }
}
