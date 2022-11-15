package cn.jack.library_weight

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @创建者 Jack
 * @创建时间 2022/11/15 15:05
 * @描述 流式布局
 */
class UFlowLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {

    //5.1、定义容器变量
    //5.1.1、记录一行中每一个View的高度
    private var mLineHeight: List<Int> = ArrayList()

    //5.1.2、记录一行中每一个View的宽度
    private var mLineWidth: List<Int> = ArrayList()

    //5.1.3、装载一行中的每个子View
    private val mLineViews: List<View> = ArrayList()

    //5.1.4、用来装mLineViews的容器
    private var mAllViews: List<List<View>> = ArrayList()

    //1、测量子View和自身宽高
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //2.定义宽高变量
        var vgWith = 0
        var vgHeight = 0

        //2.1、定义每一行的宽度/高度变量,分别用于vgWith/vgHeight取最大值
        var lineWith = 0
        var lineHeight = 0

        //2.解析出尺寸和mode信息
        //2.1、解析出尺寸信息
        var sWith = MeasureSpec.getSize(widthMeasureSpec)
        var sHeight = MeasureSpec.getSize(heightMeasureSpec)
        //2.2、解析出mode信息
        var mWidth = MeasureSpec.getMode(widthMeasureSpec)
        var mHeight = MeasureSpec.getMode(heightMeasureSpec)

        //4.遍历所有的子View
        for (i in 0 until childCount) {
            var childView = getChildAt(i)
            //4.1、特殊条件判断
            if (childView.visibility == View.GONE) {
                continue
            }

            //4.2、测量子View
            //参数2,3，表示父控件对子View的宽度和高度的一些限制条件
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            //4.3、获取子View的布局参数信息  重写了generateLayoutParams，返回的是MarginLayoutParams
            val childLp = childView.layoutParams as MarginLayoutParams
            //4.3.1、计算子View所占据的宽、高
            //父ViewGroup读取子View测量的结果有三组Api，我们使用getMeasuredWidth和getMeasuredHeight即可
            var childWith = childView.measuredWidth + childLp.leftMargin + childLp.rightMargin
            var childHeight = childView.measuredHeight + childLp.topMargin + childLp.bottomMargin

            //换行:若加入子View会超出一行的宽度，则需要换行添加
            if(vgWith + childWith > sWith - (paddingLeft + paddingRight)){
                //更新vgWith的值
                vgWith = lineWith.coerceAtLeast(childWith)
                //更新lineWith
                lineWith = childWith
                //更新lineHeight
                lineHeight = childHeight
                //高度累加
                vgHeight += lineHeight
            }else{
                //不需要换行,更新lineWith和lineHeight
                lineWith += childWith
                lineHeight = lineHeight.coerceAtLeast(childHeight)
                //在这个else中，需要增加一个判断，如果是最后一行，需要更新vgWith和vgHeight
                if(i == childCount - 1){
                    vgWith = lineWith.coerceAtLeast(childWith)
                    vgHeight += lineHeight
                }
            }
        }

        //3、自定义的View重写了onMeasure，若未调用setMeasuredDimension()方法会抛出异常
        setMeasuredDimension(
            if (mWidth == MeasureSpec.EXACTLY) sWith else (vgWith + paddingLeft + paddingRight),
            if (mHeight == MeasureSpec.EXACTLY) sHeight else (vgHeight + paddingTop + paddingBottom)
        )

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //5、摆放

    }

    //为了能在添加子View时，给子View设置正确的LayoutParams[根据需求来返回]，需要重新几个方法
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams = MarginLayoutParams(p)

    override fun generateDefaultLayoutParams(): LayoutParams = MarginLayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
    )
}