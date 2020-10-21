package com.example.administrator.bloodsoulview.voiceprogress;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
    @BindView(R.id.rtl_voice_progress)
    RtlVoiceProgress mRtlVoiceProgress;

    private int mMaxLevel = 2000;
    private int mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_progress);
        ButterKnife.bind(this);

        mVoiceProgress.setMaxLevel(mMaxLevel);
        mVoiceProgress.setLevel(mLevel);
        mVoiceProgress.setCallback(this);

        mRtlVoiceProgress.setMaxLevel(mMaxLevel);
        mRtlVoiceProgress.setLevel(mLevel);
    }

    @OnClick({R.id.sub, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sub:
                mLevel -= 100;
                mVoiceProgress.setLevel(mLevel);
                break;
            case R.id.add:
                mLevel += 100;
                mVoiceProgress.setLevel(mLevel);
                break;
        }
    }

    @Override
    public void onProgressChanged(int level, int maxLevel) {
        mMaxLevel = maxLevel;
        mLevel = level;
    }
}
