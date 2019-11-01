package com.example.administrator.bloodsoulview.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.bloodsoulview.R;
import com.example.administrator.bloodsoulview.viewpager.indicator.IndicatorActivity;
import com.example.administrator.bloodsoulview.viewpager.indicator.example.ExampleMainActivity;
import com.example.administrator.bloodsoulview.viewpager.pager.PagerActivity;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
    }

    public void clickBtn1(View view) {
        startActivity(new Intent(this, IndicatorActivity.class));
    }

    public void clickBtn2(View view) {
        startActivity(new Intent(this, ExampleMainActivity.class));
    }

    public void clickBtn3(View view) {
        startActivity(new Intent(this, PagerActivity.class));
    }
}
