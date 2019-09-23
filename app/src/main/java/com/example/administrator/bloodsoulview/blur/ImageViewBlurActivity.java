package com.example.administrator.bloodsoulview.blur;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewBlurActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_blur);
        ButterKnife.bind(this);

        //模糊
        Resources res = getResources();
        //拿到初始图
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.head);
        //处理得到模糊效果的图
        Bitmap blurBitmap = ImageFilter.blurBitmap(this, bmp, 20f);
        mImg.setImageBitmap(blurBitmap);
    }
}
