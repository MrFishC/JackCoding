package cn.jack.library_util.helper;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @创建者 Jack
 * @创建时间 2023/4/18 0018 17:18
 * @描述 设置RecyclerView GridLayoutManager or StaggeredGridLayoutManager 间距
 * <p>
 * 通过继承 RecyclerView.ItemDecoration 实现间距设置
 * <p>
 * 转载自：
 * https://blog.csdn.net/johnWcheung/article/details/54953568?spm=1001.2101.3001.6650.3&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-3-54953568-blog-106905376.235%5Ev29%5Epc_relevant_default_base3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-3-54953568-blog-106905376.235%5Ev29%5Epc_relevant_default_base3&utm_relevant_index=6
 * https://blog.csdn.net/JM_beizi/article/details/105364227
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int     spanCount;
    private int     spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置
        int column = position % spanCount; // view 所在的列

        if (includeEdge) {
            //某列的left = 列间距 - 某列的left = 列间距 - 所在的列数 * （列间距 * (1 / 列数)）
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            //outRect.right = 后列的left           //这里的推理为什么不是    某列的right = 列间距 - 后列的left   ？        一时没思考出来 改日再来
            //            打印一下数值 对比一下
//            int temp1 = spacing - (column + 1) * spacing / spanCount;
//            int temp2 = (column + 1) * spacing / spanCount;
//            System.out.println("参数信息 temp1=" + temp1 + ",temp2=" + temp2);
//            temp1=14,temp2=4
//            temp1=9,temp2=9
//            temp1=5,temp2=13
//            temp1=0,temp2=18

            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // 第一行
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        } else {
            //等间距需满足两个条件：
            //1.各个模块的大小相等，即 各列的left+right 值相等；
            //2.各列的间距相等，即 前列的right + 后列的left = 列间距；

            //公式是需要推演的[演示了当列数为2或者3的时候，验证了公式是成立的]： 资料---https://blog.csdn.net/JM_beizi/article/details/105364227
            //注：这里用的所在列数为从0开始
            outRect.left = column * spacing / spanCount; //某列的left = 所在的列数 * （列间距 * (1 / 列数)）
            outRect.right = spacing - (column + 1) * spacing / spanCount; //某列的right = 列间距 - 后列的left = 列间距 -（所在的列数+1） * （列间距 * (1 / 列数)）
            if (position >= spanCount) {    //说明不是在第一行
                outRect.top = spacing;
            }
        }
    }
}
