package cn.jack.library_util.helper;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-6-6
 * describe:RecyclerView 设置垂直和水平间距      使用类的时候，注意需要处理的问题
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int num = 3;

    public SpaceItemDecoration(int num) {
        this.num = num;
    }

    public SpaceItemDecoration(int num,int space) {
        this.num = num;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //由于每行都只有num个，所以第一个都是num的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % num==0) {
            outRect.left = 0;
        }
    }

}
