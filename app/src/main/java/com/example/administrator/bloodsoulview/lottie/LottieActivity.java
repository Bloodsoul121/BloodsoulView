package com.example.administrator.bloodsoulview.lottie;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.administrator.bloodsoulview.R;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LottieActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        ButterKnife.bind(this);

        Bitmap bitmap = loadAssetsBitmap();
        mImg.setImageBitmap(bitmap);
    }

    private Bitmap loadAssetsBitmap() {
        Bitmap bitmap = null;
        AssetManager assetManager = this.getAssets();
        try {
            InputStream inputStream = assetManager.open("emoji/img.png"); //filename是assets目录下的图片名
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
