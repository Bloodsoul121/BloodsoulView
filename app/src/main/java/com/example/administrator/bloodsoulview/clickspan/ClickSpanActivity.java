package com.example.administrator.bloodsoulview.clickspan;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.bloodsoulview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClickSpanActivity extends AppCompatActivity {

    @BindView(R.id.text)
    ClickSpanTextView mText;
    @BindView(R.id.container)
    ConstraintLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_span);
        ButterKnife.bind(this);

        initText();
    }

    private void initText() {
        String s = "ofandioangoidoggggggggggggggaoindoagidhoanogindngaoindoin" + "\n"
                + "djoangodinioagnodingoaignodngoinaiogndog" + "\n"
                + "djoangodinioagnodingoaignodngoinaiogndog" + "\n"
                + "djoangodinioagnodingoaignodngoinaiogndog" + "\n";
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(s);
        ssb.setSpan(new TextClickSpan(), 10, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(ssb);
        ClickSpanMovementMethod movementMethod = ClickSpanMovementMethod.getInstance();
        mText.setMovementMethod(movementMethod);
        mText.setLinkTouchMovementMethod(movementMethod);
        mText.setClickable(false);
    }

    @OnClick({R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.container:
                Toast.makeText(this, "click container", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class TextClickSpan extends ClickableSpan {

        @Override
        public void onClick(@NonNull View widget) {
            Toast.makeText(ClickSpanActivity.this, "click text", Toast.LENGTH_SHORT).show();
        }
    }
}
