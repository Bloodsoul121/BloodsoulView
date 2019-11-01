package com.example.administrator.bloodsoulview.viewpager.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.bloodsoulview.R;

public class TestFragment extends Fragment {
    public static final String EXTRA_TEXT = "extra_text";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.simple_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.text_view);
        Bundle bundle = getArguments();
        textView.setText(System.currentTimeMillis() + "");
        if (bundle != null) {
            textView.setText(bundle.getString(EXTRA_TEXT));
        }
    }
}
