package com.jack.simple_recycleview.customRv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 手写RecycleView，仅支持垂直方向的摆放
 */
public class RecyclerView extends ViewGroup {
    private static final String          TAG = "david";
    private              Adapter         adapter;
    private              VelocityTracker velocityTracker;
    private              Flinger         flinger;

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            //8.1、实例化回收池
            recycler = new RecycledViewPool();
        }
        scrollY = 0;
        firstRow = 0;
        needRelayout = true;
        requestLayout();
    }

    //y偏移量      内容偏移量
    private int              scrollY;
    //当前显示的View
    private List<ViewHolder> viewList;
    //11.2、当前滑动的y值
    private int              currentY;
    //3、容器要加载的数据行数
    private int              rowCount;
    //初始化
    private boolean          needRelayout;
    //   2、记录控件的宽、高
    private int              width;

    private int height;

    //    5.定义容器，记录每一行的高度
    private int[] heights;
    //    8、定义回收池
    RecycledViewPool recycler;
    //view的弟一行  是占内容的几行
    private int firstRow;
    //11.1、最小滑动距离
    private int touchSlop;
    private int maximumVelocity;

    private int minimumVelocity;

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        //    点击    28 -40  滑动
        this.flinger = new Flinger(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.minimumVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        9、容器类控件需要重写 onMeasure，重新计算子控件的高度，因为系统是没办法计算
        //        9.1、测量子控件的高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //        9.2、heightSize:根据父容器计算的高度
        int total = 0;
        if (adapter != null) {
            this.rowCount = adapter.getItemCount();
            heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = adapter.getHeight(i);
                total += adapter.getHeight(i);
            }
        }

        int h = Math.min(heightSize, total);
        setMeasuredDimension(widthMeasureSpec, h);
    }

    //onLayout  1     n      会调用多次  比测量耗时
    //    1.onLayout 会被调用多次,比测量耗时
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //1.1.needRelayout：自行控制摆放，changed：根据系统的参数来控制摆放
        //1.2.needRelayout设置为true的时机：设置了adapter之后
        if (needRelayout || changed) {
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            if (adapter != null) {
                //4.根据参数计算recycleview的宽、高，在系统的RecycleView中，
                // 是由LayoutManager来,我们就在adapter中定义获取宽、高的方法
                width = r - l;
                height = b - t;
                int left, top = 0, right, bottom;
                //                第一行不是从0开始
                top = -scrollY;
                this.rowCount = adapter.getItemCount();

                heights = new int[rowCount];
                for (int i = 0; i < heights.length; i++) {
                    heights[i] = adapter.getHeight(i);
                }
                //                rowCount  内容的item数量 1000           height 当前控件的高度
                //                6.开始摆放
                //                10、若recycleView加载的列表过多，会非常慢  这里需要优化，只加载一屏:若最后一个View的上边界(top)超过了一屏的高度则不需要加载

                for (int i = 0; i < rowCount && top < height; i++) {
                    bottom = top + heights[i];
                    //                    6.1、填充View，摆放View
                    //生成View
                    ViewHolder viewHolder = makeAndStep(i, 0, top, width, bottom);
                    //        6.3、缓存ViewHolder   使用一级缓存
                    viewList.add(viewHolder);
                    //                    6.2、循环条件
                    top = bottom;
                }
            }
        }

    }

    private ViewHolder makeAndStep(int row, int left, int top, int right, int bottom) {
        //        实例化一个有宽度  高度的View
        ViewHolder viewHolder = obtainView(row, right - left, bottom - top);
        //        设置位置
        //        6.4、摆放View
        viewHolder.itemView.layout(left, top, right, bottom);
        //        6.5、返回ViewHolder
        return viewHolder;
    }

    private ViewHolder obtainView(int row, int width, int height) {
        //        6.6、实例化ViewHolder
        //        6.7/获取到对应的布局类型
        int itemType = adapter.getItemViewType(row);

        //        6.8、先从二级缓存中查找，若二级缓存查找的为空，则从四级缓存（重点）中查找 todo


        //根据这个类型 返回相应View  （布局）
        //        初始化的时候 取不到

        //        8.2、从四级缓存中找
        ViewHolder reclyView = recycler.getRecycledView(itemType);
        Log.i(TAG, "obtainView: " + reclyView == null ? "是空的" : "不是空的");
        if (reclyView == null) {
            //            8.3、回收池中没有ViewHolder，则从适配中查找
            reclyView = adapter.onCreateViewHolder(this, itemType);
        }

        //        8.4、这里若reclyView仍为空，则是因为程序员不严谨的原因，抛出异常

        //        8.5、更新
        adapter.onBindViewHolder(reclyView, row);
        //View不可能为空
        //
        reclyView.setItemViewType(itemType);
        //        测量
        //VIEW 打tag   row    type
        //        8.6、直接测量
        reclyView.getItemView().measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        //        8.7、添加到RecycleView上
        addView(reclyView.getItemView(), 0);

        return reclyView;
    }

    private int sumArray(int array[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    //拦截 滑动事件  预处理 事件的过程

    //    11、拦截事件 需要重写onInterceptTouchEvent
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int y2 = Math.abs(currentY - (int) event.getRawY());
                if (y2 > touchSlop) {
                    //                    11.3、产生滑动，拦截
                    intercept = true;
                }
                break;
            }
        }
        return intercept;
    }


    //    12、事件处理，重写onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);

                int velocityY = (int) velocityTracker.getYVelocity();

                int initY = scrollY + sumArray(heights, 1, firstRow);
                int maxY = Math.max(0, sumArray(heights, 0, heights.length) - height);
                //                判断是否开启 惯性滑动
                if (Math.abs(velocityY) > minimumVelocity) {
                    //                        线程  ---》自己看线程
                    flinger.start(0, initY, 0, velocityY, 0, maxY);
                } else {

                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }

                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                //                移动的距离   y方向
                int y2 = (int) event.getRawY();
                //   diffX>0    往左划
                //12.1、diffY > 0 上滑
                int diffY = currentY - y2;
                //12.2、相对现在的位置滚动 diffY
                scrollBy(0, diffY);
                break;
            }

        }
        return super.onTouchEvent(event);
    }

    //scrollBy
    @Override
    public void scrollBy(int x, int y) {
        scrollY += y;
        //     scrollY取值   0 ---- 屏幕 的高度   0---无限大   2
        //修正一下  内容的总高度 是他的边界值
        scrollY = scrollBounds(scrollY, firstRow, heights, height);
        if (scrollY > 0) {
            //            往上滑
            while (heights[firstRow] < scrollY) {
                //              remove  item完全移出去了  应该 1  不应该2
                if (!viewList.isEmpty()) {
                    removeView(viewList.remove(0));
                }
                scrollY -= heights[firstRow];
                firstRow++;
            }
            //            scrollY=0

            while (getFilledHeight() < height) {
                int dataIndex = firstRow + viewList.size();
                ViewHolder view = obtainView(dataIndex, width,
                        heights[dataIndex]);
                viewList.add(viewList.size(), view);
            }

        } else if (scrollY < 0) {
            //            往下滑
            while (!viewList.isEmpty() && getFilledHeight() - heights[firstRow + viewList.size() - 1] >= height) {
                removeView(viewList.remove(viewList.size() - 1));
            }

            while (0 > scrollY) {
                ViewHolder viewHolder = obtainView(firstRow - 1, width, heights[0]);
                viewList.add(0, viewHolder);
                firstRow--;
                scrollY += heights[firstRow + 1];
            }
        }
        //        重新对一个子控件进行重新layout
        repositionViews();
    }

    private void repositionViews() {
        int left, top, right, bottom, i;
        top = -scrollY;
        i = firstRow;
        for (ViewHolder viewHolder : viewList) {
            bottom = top + heights[i++];
            viewHolder.itemView.layout(0, top, width, bottom);
            top = bottom;
        }
    }

    private int getFilledHeight() {
        return sumArray(heights, firstRow, viewList.size()) - scrollY;
    }

    private int scrollBounds(int scrollY, int firstRow, int sizes[], int viewSize) {
        if (scrollY > 0) {
            Log.i(TAG, " 上滑 scrollBounds: scrollY  " + scrollY + "  各项之和  " + sumArray(sizes, firstRow, sizes.length - firstRow) + "  receryView高度  " + viewSize);
            //            往上滑  bug +
            if (sumArray(sizes, firstRow, sizes.length - firstRow) - scrollY > viewSize) {
                scrollY = scrollY;
            } else {
                scrollY = sumArray(sizes, firstRow, sizes.length - firstRow) - viewSize;
            }
        } else {
            //            往下滑  y  firstRow= 0    -
            scrollY = Math.max(scrollY, -sumArray(sizes, 0, firstRow));  //=0
            //            scrollY = Math.max(scrollY, 0);  //=
            Log.i(TAG, "下滑  scrollBounds: scrollY  " + scrollY + "  各项之和  " + (-sumArray(sizes, 0, firstRow)));
        }
        return scrollY;
    }

    public void removeView(ViewHolder viewHolder) {
        int typeView = viewHolder.getItemViewType();
        recycler.putRecycledView(viewHolder, typeView);
        removeView(viewHolder.getItemView());
    }

    interface Adapter<VH extends ViewHolder> {
        VH onCreateViewHolder(ViewGroup parent, int viewType);

        VH onBindViewHolder(VH viewHodler, int position);

        //Item的类型
        int getItemViewType(int position);

        int getItemCount();

        public int getHeight(int index);
    }

    class Flinger implements Runnable {
        //        scrollBy   （移动的偏移量)  而不是速度
        private Scroller scroller;
        //
        private int      initY;

        void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY) {
            scroller.fling(initX, initY, initialVelocityX
                    , initialVelocityY, 0, maxX, 0, maxY);
            this.initY = initY;
            post(this);
        }

        Flinger(Context context) {
            scroller = new Scroller(context);
        }

        @Override
        public void run() {
            if (scroller.isFinished()) {
                return;
            }

            boolean more = scroller.computeScrollOffset();
            //
            int y = scroller.getCurrY();
            int diffY = initY - y;
            if (diffY != 0) {
                scrollBy(0, diffY);
                initY = y;
            }
            if (more) {
                post(this);
            }
        }

        boolean isFinished() {
            return scroller.isFinished();
        }
    }
}
