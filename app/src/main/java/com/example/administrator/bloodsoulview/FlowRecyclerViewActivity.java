package com.example.administrator.bloodsoulview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.bloodsoulview.flow.StudyLayoutManager;
import com.example.administrator.bloodsoulview.flow.WaterfallAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlowRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.container)
    LinearLayout mContainer;

    private WaterfallAdapter mAdapter;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_recycler_view);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
//        layoutManager.setFlexWrap(FlexWrap.WRAP); //设置是否换行
//        layoutManager.setFlexDirection(FlexDirection.ROW); // 设置主轴排列方式
//        layoutManager.setAlignItems(AlignItems.STRETCH);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

//        FlowLayoutManager layoutManager = new FlowLayoutManager(this);

        StudyLayoutManager layoutManager = new StudyLayoutManager();

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new WaterfallAdapter(this, mOnClickListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();
    }

    private void loadDatas() {

        final String[] tags = {"婚姻育儿", "散文婚姻育儿婚姻育儿婚姻育儿婚姻育儿", "设计婚姻育儿", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身婚姻育儿婚姻育儿", "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影",
                "婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说婚姻育儿", "运动和健身", "工具癖", "生活家婚姻育儿", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};

        final ArrayList<String> datas = new ArrayList<>(Arrays.asList(tags));

        mAdapter.setDatas(datas);
    }

}
