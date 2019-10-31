package com.example.administrator.bloodsoulview.viewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.bloodsoulview.R;
import com.example.administrator.bloodsoulview.viewpager.indicator.IndicatorActivity;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
    }

    public void clickBtn1(View view) {
        startActivity(new Intent(this, IndicatorActivity.class));
    }
}
