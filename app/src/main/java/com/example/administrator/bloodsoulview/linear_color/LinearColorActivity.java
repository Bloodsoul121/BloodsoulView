package com.example.administrator.bloodsoulview.linear_color;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinearColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.linear_color)
    View mLinearColor;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.result_color)
    View mResultColor;

    private int mMax = 100;
    private LinearGradientUtil mLinearGradientUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liear_color);
        ButterKnife.bind(this);

        mSeekBar.setMax(mMax);
        mSeekBar.setOnSeekBarChangeListener(this);

        mLinearGradientUtil = new LinearGradientUtil(getColor(R.color.color_linear_start), getColor(R.color.color_linear_end));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int color = mLinearGradientUtil.getColor(progress * 1f / mMax);
        mResultColor.setBackgroundColor(color);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
