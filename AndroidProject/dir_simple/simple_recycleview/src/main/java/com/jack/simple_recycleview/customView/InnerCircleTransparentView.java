package com.jack.simple_recycleview.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.jack.simple_recycleview.R;

/**
 * @author Jack
 * @time 19-10-31
 * @describe 目前根据需求定制的
 */
public class InnerCircleTransparentView extends LinearLayout {

    public InnerCircleTransparentView(Context context) {
        this(context,null);
    }

    public InnerCircleTransparentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InnerCircleTransparentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        setOrientation(LinearLayout.HORIZONTAL);

        //添加两个子view
        if (getChildCount() > 0) {
            removeAllViews();
        }

        //inflate系列方法 : 内部实现原理就是利用Pull解析器，对Xml文件进行解析，然后返回View对象

        //当我们传进来的root参数不是空的时候，并且attachToRoot是false的时候，会给temp(请看源码)设置一个LayoutParams参数。
        View customLayoutLeft = LayoutInflater.from(getContext()).inflate(R.layout.layout_inner_circle_transparent_left,this,false);
        View customLayoutRight = LayoutInflater.from(getContext()).inflate(R.layout.layout_inner_circle_transparent_right,this,false);

        addView(customLayoutLeft);
        addView(customLayoutRight);

    }

}
