package com.example.administrator.bloodsoulview.bigimg;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.administrator.bloodsoulview.R;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BigImgActivity extends AppCompatActivity {

    @BindView(R.id.big_img_view)
    BigImgView mBigImgView;
    @BindView(R.id.large_img_view)
    LargeImageView mLargeImgView;
    @BindView(R.id.status)
    Button mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        ButterKnife.bind(this);

        mStatus.setText("BigImgView");
        mBigImgView.setVisibility(View.VISIBLE);
        mLargeImgView.setVisibility(View.GONE);

        try {
//            InputStream is = getAssets().open("aaa.png");
            InputStream is = getAssets().open("bbb.jpg");
            mBigImgView.setImage(is);
            mLargeImgView.setImage(new InputStreamBitmapDecoderFactory(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.status)
    public void onViewClicked() {
        if (mBigImgView.getVisibility() == View.VISIBLE) {
            mStatus.setText("LargeImageView");
            mBigImgView.setVisibility(View.GONE);
            mLargeImgView.setVisibility(View.VISIBLE);
        } else if (mLargeImgView.getVisibility() == View.VISIBLE) {
            mStatus.setText("BigImgView");
            mBigImgView.setVisibility(View.VISIBLE);
            mLargeImgView.setVisibility(View.GONE);
        } else {
            mStatus.setText("no view");
        }
    }
}
