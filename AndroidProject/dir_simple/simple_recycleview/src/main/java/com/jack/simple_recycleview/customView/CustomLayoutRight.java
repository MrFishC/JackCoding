package com.jack.simple_recycleview.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Jack
 * @time 19-10-31
 * @describe
 *
 * 灵感来自 https://blog.csdn.net/qq_20785431/article/details/81159210
 *
 */
public class CustomLayoutRight extends RelativeLayout {

    private Context mContext;
    private CustomDrawable background;

    public CustomLayoutRight(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public CustomLayoutRight(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView(context, attrs, 0);
    }

    public CustomLayoutRight(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        background = new CustomDrawable(getBackground());
        setBackground(background);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resetBackgroundHoleArea();
    }

    @SuppressLint("NewApi")
    private void resetBackgroundHoleArea() {
        Path path = null;

        // 以子View为范围构造需要透明显示的区域

        if (getChildCount() > 0) {

            View view  = getChildAt(0);

            if (view != null) {
                path = new Path();
                path.addCircle(view.getRight(),(view.getTop() + view.getBottom())/2 ,view.getWidth(), Path.Direction.CW);
            }
            if (path != null) {
                background.setSrcPath(path);
            }
        }


    }

}
