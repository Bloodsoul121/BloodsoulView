package com.example.administrator.bloodsoulview.flow;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.List;

public class FlowLayoutManager extends RecyclerView.LayoutManager {

    private Context mContext;

    /**
     *  总 height
     */
    private int mTotalHeight = 0;

    /**
     *  当前行的第一个 view position
     */
    private int mCurrentLineStartPosition = 0;

    /**
     *  添加容器时，起点 position
     */
    private int mAddContainerStartPosition = -1;

    /**
     *  添加容器时，终点 position
     */
    private int mAddContainerEndPosition = -1;

    /**
     * 滑动偏移量
     * 如果是正的就是在向上滑，展现上面的view
     * 如果是负的向下
     */
    private int mVerticalScrollOffset = 0;

    /**
     *  判断是否超过宽的2/3
     */
    private boolean mIsMeasureOver = false;

    /**
     *  当前的 LinearLayout 容器
     */
    private LinearLayout mCurrentLineLinearLayout;

    /**
     *  第一个 view 是否超过整个宽长
     */
    private boolean mIsFirstViewMeasureOverWidth = false;

    /**
     *  view 尺寸
     */
    private SparseArray<Rect> mAllItemframs = new SparseArray<>();

    /**
     *  view 的外部容器 起点 和 终点 position
     */
    private SparseIntArray mLinePositionContainers = new SparseIntArray();

    public FlowLayoutManager(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }

        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler);

        //开始摆放
        int offsetX = 0;
        int offsetY = 0;
        int viewH = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);//因为进行了detach操作，所以现在要重新添加
            measureChildWithMargins(view, 0, 0);//通知测量itemView

            //获取itemVIEW的实际大小，包括measureChildWithMargins方法中设置的大小
            int w = getDecoratedMeasuredWidth(view);
            int h = getDecoratedMeasuredHeight(view);
            viewH = h;
            Rect fram = mAllItemframs.get(i);
            if (fram == null) {
                fram = new Rect();
            }

            // 第二次超过宽的2/3，换行
            if (mIsMeasureOver && offsetX + w > getWidth() * 2 / 3) {

                mIsMeasureOver = false;
                mCurrentLineStartPosition = i;

                // 换行的View的值
                offsetY += h;
                offsetX = w;
                fram.set(0, offsetY, w, offsetY + h);

            } else if (!mIsMeasureOver && offsetX + w > getWidth() * 2 / 3) {

                // 第一次超过宽的2/3

                mIsMeasureOver = true;

                // 不需要换行
                fram.set(offsetX, offsetY, offsetX + w, offsetY + h);
                offsetX += w;

                // 判断是否需要添加外部容器
                if (offsetX + w > getWidth()) {
                    mLinePositionContainers.put(mCurrentLineStartPosition, i);

                    if (i == 0) {
                        mIsFirstViewMeasureOverWidth = true;
                    }
                }

            } else {
                // 不需要换行
                fram.set(offsetX, offsetY, offsetX + w, offsetY + h);
                offsetX += w;
            }

            // 要针对于当前View, 生成对应的Rect, 然后放到allItemframs
            mAllItemframs.put(i, fram);
        }

        // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
        // 包括ItemDecorator设置的距离。
        mTotalHeight = offsetY + viewH;
        recyclerViewFillView(recycler, state);
    }

    private void recyclerViewFillView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 清空RecyclerView的子View
        detachAndScrapAttachedViews(recycler);// 将当前getchildcount数量的子View放入到scrap缓存池去
        Rect phoneFrame = new Rect(0, mVerticalScrollOffset, getWidth(), mVerticalScrollOffset + getHeight());//当前可见区域
        //将滑出屏幕的view进行回收
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect child = mAllItemframs.get(i);
            if (!Rect.intersects(phoneFrame, child)) {
                if (childView != null) {
                    removeAndRecycleView(childView, recycler);//recyclerview移除当前childview，并将之放入到recycler缓存池
                }
            }
        }
        //可见区域出现在屏幕上的子view
        for (int j = 0; j < getItemCount(); j++) {
            if (Rect.intersects(phoneFrame, mAllItemframs.get(j))) {
                // scrap回收池里面拿的
                View scrap = recycler.getViewForPosition(j);
                measureChildWithMargins(scrap, 0, 0);

                int position = mLinePositionContainers.get(j);
                if (j == 0 && mIsFirstViewMeasureOverWidth) {
                    // 第一个 view 占据整个宽长
                    LinearLayout linearLayout = generateLinearLayout();
                    linearLayout.addView(scrap);
                    HorizontalScrollView scrollView = generateHorizontalScrollView(recycler, 0);
                    scrollView.addView(linearLayout);
                    addView(scrollView);
                    Rect frame = mAllItemframs.get(j);
                    layoutDecorated(scrollView, 0, frame.top - mVerticalScrollOffset, getWidth(), frame.bottom - mVerticalScrollOffset);//给每一个itemview进行布局
                } else if (position > 0) {
                    mAddContainerStartPosition = j;
                    mAddContainerEndPosition = position;
                    mCurrentLineLinearLayout = generateLinearLayout();
                    mCurrentLineLinearLayout.addView(scrap);
                } else if (mAddContainerStartPosition < j && j < mAddContainerEndPosition) {
                    mCurrentLineLinearLayout.addView(scrap);
                } else if (j == mAddContainerEndPosition) {
                    mCurrentLineLinearLayout.addView(scrap);
                    HorizontalScrollView currentLineHorizontalScrollView = generateHorizontalScrollView(recycler, mCurrentLineStartPosition);
                    currentLineHorizontalScrollView.addView(mCurrentLineLinearLayout);
                    addView(currentLineHorizontalScrollView);
                    Rect frame = mAllItemframs.get(j);
                    layoutDecorated(currentLineHorizontalScrollView, 0, frame.top - mVerticalScrollOffset, getWidth(), frame.bottom - mVerticalScrollOffset);//给每一个itemview进行布局
                } else {
                    addView(scrap);
                    Rect frame = mAllItemframs.get(j);
                    layoutDecorated(scrap, frame.left, frame.top - mVerticalScrollOffset, frame.right, frame.bottom - mVerticalScrollOffset);//给每一个itemview进行布局
                }
            }
        }
    }

    @NonNull
    private HorizontalScrollView generateHorizontalScrollView(RecyclerView.Recycler recycler, int position) {
        HorizontalScrollView scrollView = new HorizontalScrollView(mContext);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.setLayoutParams(layoutParams);
        return scrollView;
    }

    @NonNull
    private LinearLayout generateLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 实际滑动距离  dx
        int trval = dy;

        if (mVerticalScrollOffset + dy < 0) {//如果滑动到最顶部
            trval = -mVerticalScrollOffset;
        } else if (mVerticalScrollOffset + dy > mTotalHeight - getHeight()) {
            // 如果滑动到最底部  往上滑   mVerticalScrollOffset   +
            trval = mTotalHeight - getHeight() - mVerticalScrollOffset;
        }

        mVerticalScrollOffset += trval;
        //平移容器内的item
        offsetChildrenVertical(trval);
        recyclerViewFillView(recycler, state);//回收滑出去的itemview，并从缓存中拿出进行复用

        return trval;
    }

}
