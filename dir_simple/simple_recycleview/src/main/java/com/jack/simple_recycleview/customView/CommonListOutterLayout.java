package com.jack.simple_recycleview.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.jack.simple_recycleview.R;

/**
 * @author Jack
 * @time 19-9-17 下午2:09
 * @describe 通用列表背景
 */
public class CommonListOutterLayout extends LinearLayout {

    private int mBackGround;

    public CommonListOutterLayout(Context context) {
        this(context,null);
    }

    public CommonListOutterLayout(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CommonListOutterLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    //初始化控件
    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = null;
        if(attrs != null){
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonListOutterLayout);
            mBackGround = typedArray.getColor(R.styleable.CommonListOutterLayout_bg,R.drawable.icon_common_bg);
            typedArray.recycle();
        }

        setOrientation(LinearLayout.VERTICAL);

        //设置背景色
        setBackground(new BitmapDrawable(getResources(),ResourceFile2Bitmap()));
    }

    /**
     * 图片转bitmap
     * @return
     */
    public Bitmap ResourceFile2Bitmap(){
        //修改R.drawable.pic为你自己的文件即可
        return BitmapFactory.decodeResource(getResources(), mBackGround);                           //此处实现文件类型的转换
    }

}
