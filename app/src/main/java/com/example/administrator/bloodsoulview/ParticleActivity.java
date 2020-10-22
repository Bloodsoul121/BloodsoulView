package com.example.administrator.bloodsoulview;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.bloodsoulview.particle.DimpleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParticleActivity extends AppCompatActivity {

    @BindView(R.id.head_iv)
    ImageView mHeadIv;
    @BindView(R.id.dimple_view)
    DimpleView mDimpleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle);
        ButterKnife.bind(this);

        Glide.with(this)
                .asBitmap()
                .load(R.drawable.cat)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .override(SizeUtil.dp2px(200f), SizeUtil.dp2px(200f))
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mHeadIv);
    }

    @OnClick(R.id.dimple_view)
    public void onViewClicked() {
        mDimpleView.start();
    }
}
