package com.example.administrator.bloodsoulview.svga;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SvgaActivity extends AppCompatActivity {

    @BindView(R.id.svgaview)
    WrapSVGAImageView mSvgaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svga);
        ButterKnife.bind(this);
    }

    public void clickBtn1(View view) {
        mSvgaView.loadSvgaAssets("speak_wave_anim_man.svga");
    }

    public void clickBtn2(View view) {
        mSvgaView.loadSvgaAssets("speak_wave_anim_woman.svga");
    }

    public void clickBtn3(View view) {
        mSvgaView.loadSvgaAssets("tm.svga");
    }

    public void clickBtn4(View view) {
        mSvgaView.setLoops(1);
        mSvgaView.setClearsAfterStop(false);
        mSvgaView.loadSvgaAssets("livehongbao.svga");
    }
}
