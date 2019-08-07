package com.example.administrator.bloodsoulview;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.bloodsoulview.util.SizeUtil;
import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowActivity extends AppCompatActivity {

    private static final String TAG = FlowActivity.class.getSimpleName();

    @BindView(R.id.flexbox_layout)
    FlexboxLayout mFlexboxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] tags = {"婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身", "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};
        for (String tag : tags) {
            mFlexboxLayout.addView(createNewFlexItemTextView(tag));
        }
    }

    private TextView createNewFlexItemTextView(String text) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.color_222222, null));
        textView.setBackgroundResource(R.drawable.text_bubble_item_recommend_bg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, ((TextView)view).getText().toString());
            }
        });
        int paddingLeftAndRight = SizeUtil.dp2px(19);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, 0, paddingLeftAndRight, 0);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = SizeUtil.dp2px(34);
        int margin = SizeUtil.dp2px(10);
        layoutParams.setMargins(margin, margin, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }
}
