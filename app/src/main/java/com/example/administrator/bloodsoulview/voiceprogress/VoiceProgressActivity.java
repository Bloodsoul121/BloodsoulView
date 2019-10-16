package com.example.administrator.bloodsoulview.voiceprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceProgressActivity extends AppCompatActivity implements VoiceProgress.Callback {

    @BindView(R.id.voice_progress)
    VoiceProgress mVoiceProgress;
    @BindView(R.id.sub)
    Button mSub;
    @BindView(R.id.add)
    Button mAdd;

    private int mMaxLevel = 10;
    private int mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_progress);
        ButterKnife.bind(this);

        mVoiceProgress.setMaxLevel(mMaxLevel);
        mVoiceProgress.setLevel(mLevel);
        mVoiceProgress.setCallback(this);
    }

    @OnClick({R.id.sub, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sub:
                mVoiceProgress.setLevel(--mLevel);
                break;
            case R.id.add:
                mVoiceProgress.setLevel(++mLevel);
                break;
        }
    }

    @Override
    public void onProgressChanged(int level, int maxLevel) {
        mMaxLevel = maxLevel;
        mLevel = level;
    }
}
