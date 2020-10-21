package com.example.administrator.bloodsoulview.linear_color;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
    @BindView(R.id.high_result_color)
    View mHighResultColor;
    @BindView(R.id.high_seek_bar)
    SeekBar mHighSeekBar;

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

        // 另一种高级的取色
        initColorBar();
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

    private void initColorBar() {
        final ColorPickGradient colorPickGradient = new ColorPickGradient();

        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0, width, height,
                        ColorPickGradient.PICKCOLORBAR_COLORS,
                        ColorPickGradient.PICKCOLORBAR_POSITIONS,
                        Shader.TileMode.REPEAT);
            }
        };
        PaintDrawable paint = new PaintDrawable();
        paint.setShape(new RectShape());
        paint.setCornerRadius(10);
        paint.setShaderFactory(shaderFactory);
        mHighSeekBar.setProgressDrawable(paint);


        //当SeekBar被滑动时,获取颜色
        mHighSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float radio = (float) progress / mHighSeekBar.getMax();
                int color = colorPickGradient.getColor(radio);
                mHighResultColor.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
