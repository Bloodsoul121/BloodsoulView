package com.example.administrator.bloodsoulview.flow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.bloodsoulview.R;
import com.example.administrator.bloodsoulview.util.SizeUtil;

import java.util.ArrayList;

public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private View.OnClickListener mOnClickListener;
    private SparseArray<HorizontalScrollView> mScrollViews = new SparseArray<>();

    public WaterfallAdapter(Context context, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mOnClickListener = onClickListener;
    }

    public void setDatas(ArrayList<String> datas) {
        this.mDatas = datas;

        // 计算
        computeLayout();

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        HorizontalScrollView horizontalScrollView = mScrollViews.get(position);
        if (horizontalScrollView == null) {
            return -1;
        }
        return position;
    }

    @NonNull
    @Override
    public WaterfallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == -1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_text_bubble, parent, false);
        } else {
            view = mScrollViews.get(viewType);
        }
        return new WaterfallAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterfallAdapter.ViewHolder holder, int position) {
        if (mScrollViews.get(position) == null) {
            holder.mTv.setText(mDatas.get(position));
            holder.mTv.setOnClickListener(mOnClickListener);
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        ViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.text_view);
        }

    }

    private void computeLayout() {
        mScrollViews.clear();

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_text_bubble, null);
        TextView mTv = view.findViewById(R.id.text_view);

        int offsetX = 0;
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int recyclerWidth = dm.widthPixels;
        int mCurrentLineStartPosition = 0;
        boolean mIsMeasureOver = false;
        SparseIntArray mLinePositionContainers = new SparseIntArray();

        for (int i = 0; i < mDatas.size(); i++) {

            int w = (int) (mTv.getPaint().measureText(mDatas.get(i)) + mTv.getPaddingStart() + mTv.getPaddingEnd() + SizeUtil.dp2px(10) * 2);

            // 第二次超过宽的2/3，换行
            if (mIsMeasureOver && offsetX + w > recyclerWidth * 2 / 3) {

                mIsMeasureOver = false;
                mCurrentLineStartPosition = i;

                // 换行的View的值
                offsetX = w;

            } else if (!mIsMeasureOver && offsetX + w > recyclerWidth * 2 / 3) {

                // 第一次超过宽的2/3

                mIsMeasureOver = true;

                // 不需要换行
                offsetX += w;

                // 判断是否需要添加外部容器
                if (offsetX + w > recyclerWidth) {

                    mLinePositionContainers.put(mCurrentLineStartPosition, i);
                }

            } else {
                // 不需要换行
                offsetX += w;
            }
        }

        int offset = 0;

        for (int i = 0; i < mLinePositionContainers.size(); i++) {
            int startPosition = mLinePositionContainers.keyAt(i) - offset;
            int endPosition = mLinePositionContainers.valueAt(i) - offset;

            LinearLayout linearLayout = generateLinearLayout();
            for (int j = startPosition; j <= endPosition; j++) {
                View child = LayoutInflater.from(mContext).inflate(R.layout.layout_item_text_bubble, null);
                TextView tv = child.findViewById(R.id.text_view);
                tv.setText(mDatas.get(startPosition));
                child.setOnClickListener(mOnClickListener);
                linearLayout.addView(child);
                mDatas.remove(startPosition);
            }
            mDatas.add(startPosition, "");

            offset += (endPosition - startPosition);

            HorizontalScrollView horizontalScrollView = generateHorizontalScrollView();
            horizontalScrollView.addView(linearLayout);
            mScrollViews.put(startPosition, horizontalScrollView);
        }

    }

    @NonNull
    private HorizontalScrollView generateHorizontalScrollView() {
        HorizontalScrollView scrollView = new HorizontalScrollView(mContext);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(layoutParams);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        scrollView.setHorizontalScrollBarEnabled(false);
        return scrollView;
    }

    @NonNull
    private LinearLayout generateLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

}
