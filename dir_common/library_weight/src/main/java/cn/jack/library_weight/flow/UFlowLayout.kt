package cn.jack.library_weight.flow

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @创建者 Jack
 * @创建时间 2022/11/15 15:05
 * @描述 流式布局
 */
open class UFlowLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ViewGroup(context, attrs, defStyle) {

    //5.1、定义容器变量
    //5.1.1、记录一行中每一个View的高度
    private var mLineHeight: MutableList<Int>? = null

    //5.1.2、记录一行中每一个View的宽度
    private var mLineWidth: MutableList<Int>? = null

    //5.1.3、装载一行中的每个子View
    private var mLineViews: MutableList<View>? = null

    //5.1.4、用来装mLineViews的容器
    private var mTotalLine: MutableList<MutableList<View>>? = null

    //1、测量子View和自身宽高
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //待研究：为什么这里的onMeasure方法会执行三次
        println("UFlowLayout onMeasure")
        println("UFlowLayout " + System.currentTimeMillis())

        //2.定义宽高变量
        var vgWith = 0
        var vgHeight = 0

        //2.1、定义每一行的宽度/高度变量,分别用于vgWith/vgHeight取最大值
        var lineWith = 0
        var lineHeight = 0

        //2.解析出尺寸和mode信息
        //2.1、解析出尺寸信息
        val sWith = MeasureSpec.getSize(widthMeasureSpec)
        val sHeight = MeasureSpec.getSize(heightMeasureSpec)
        //2.2、解析出mode信息
        val mWidth = MeasureSpec.getMode(widthMeasureSpec)
        val mHeight = MeasureSpec.getMode(heightMeasureSpec)

        //4.遍历所有的子View
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            //4.1、特殊条件判断
            if (childView.visibility == View.GONE) {
                if (i == childCount - 1) {
                    vgWith = lineWith.coerceAtLeast(vgWith)
                    vgHeight += lineHeight
                }
                continue
            }

            //4.2、测量子View
            //参数2,3，表示父控件对子View的宽度和高度的一些限制条件
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            //4.3、获取子View的布局参数信息  重写了generateLayoutParams，返回的是MarginLayoutParams
            val childLp = childView.layoutParams as MarginLayoutParams
            //4.3.1、计算子View所占据的宽、高
            //父ViewGroup读取子View测量的结果有三组Api，我们使用getMeasuredWidth和getMeasuredHeight即可
            val childWith = childView.measuredWidth + childLp.leftMargin + childLp.rightMargin
            val childHeight = childView.measuredHeight + childLp.topMargin + childLp.bottomMargin

            //换行:若加入子View会超出一行的宽度，则需要换行添加
            if (lineWith + childWith > sWith - (paddingLeft + paddingRight)) {
                //更新vgWith的值
                vgWith = vgWith.coerceAtLeast(lineWith)
                //更新lineWith
                lineWith = childWith
                //高度累加
                vgHeight += lineHeight
                //更新lineHeight
                lineHeight = childHeight
            } else {
                //不需要换行,更新lineWith和lineHeight
                lineWith += childWith
                lineHeight = lineHeight.coerceAtLeast(childHeight)
            }

            if (i == childCount - 1) {
                vgWith = lineWith.coerceAtLeast(childWith)
                vgHeight += lineHeight
            }

            //修复：应该使用lineWith + childWith
//            if (vgWith + childWith > sWith - (paddingLeft + paddingRight)) {
//                //更新vgWith的值
//                vgWith = lineWith.coerceAtLeast(childWith) //修复：使用vgWith同childWith比较，取最大值
//                //更新lineWith
//                lineWith = childWith
//                //高度累加
//                vgHeight += lineHeight
//                //更新lineHeight
//                lineHeight = childHeight
//            } else {
//                //不需要换行,更新lineWith和lineHeight
//                lineWith += childWith
//                lineHeight = lineHeight.coerceAtLeast(childHeight)
//            }
//
//            if (i == childCount - 1) {
//                vgWith = lineWith.coerceAtLeast(childWith)
//                vgHeight += lineHeight
//            }
        }

        //3、自定义的View重写了onMeasure，若未调用setMeasuredDimension()方法会抛出异常
        setMeasuredDimension(
            if (mWidth == MeasureSpec.EXACTLY) sWith else (vgWith + paddingLeft + paddingRight),
            if (mHeight == MeasureSpec.EXACTLY) sHeight else (vgHeight + paddingTop + paddingBottom)
        )

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        println("UFlowLayout onLayout")

        //5、摆放
        mLineHeight = mutableListOf()
        mLineWidth = mutableListOf()
        mLineViews = mutableListOf()
        mTotalLine = mutableListOf()

        var lineWidth = 0
        var lineHeight = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == GONE) {
                continue
            }
            val childLp = childView.layoutParams as MarginLayoutParams
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight
            val childMarginLR = childLp.leftMargin + childLp.rightMargin
            val childMarginTB = childLp.topMargin + childLp.bottomMargin

            //需要换行
            if (childWidth + lineWidth + childMarginLR > width - (paddingLeft + paddingRight)) {
                mLineHeight!!.add(lineHeight)
                mLineWidth!!.add(lineWidth)
                mTotalLine!!.add(mLineViews!!)

                //重置lineWidth
                lineWidth = 0
                //更新 lineHeight
                lineHeight = childHeight + childMarginTB
                //重置mLineViews
                mLineViews = mutableListOf()
            }

            //累加操作
//            lineWidth = childWidth + childMarginLR
            lineWidth += childWidth + childMarginLR //修复：lineWidth值更新错误
            lineHeight = lineHeight.coerceAtLeast(childHeight + childMarginTB)
            mLineViews!!.add(childView)
        }

        //最后一行  这里的逻辑需要借助UI来对比会清晰一些
        mLineHeight!!.add(lineHeight)
        mLineWidth!!.add(lineWidth)
        mTotalLine!!.add(mLineViews!!)

//        var left = paddingLeft
        var top = paddingTop

        //开始摆放子View
        for (i in 0 until mTotalLine!!.size) {
            mLineViews = mTotalLine!![i]
            lineHeight = mLineHeight!![i]

            //设置UFlowLayout默认居中  不太好看
//            var currentViewWidth = mLineWidth!![i]

//            left = paddingLeft
//            var left = paddingLeft  //left变量在循环内部定义，每次循环，left的值都会初始化为paddingLeft
//            var left = (width - currentViewWidth) / 2 + paddingLeft

            for (k in 0 until mLineViews!!.size) {
                val childView = mLineViews!![k]
                if (childView.visibility == GONE) {
                    continue
                }

                val childLp = childView.layoutParams as MarginLayoutParams

                val childTop = top + childLp.topMargin
                val childBottom = childTop + childView.measuredHeight
                val childLeft = left + childLp.leftMargin
                val childRight = childLeft + childView.measuredWidth

                childView.layout(childLeft, childTop, childRight, childBottom)

//                left = childView.measuredWidth + childLp.leftMargin + childLp.rightMargin
                left += childView.measuredWidth + childLp.leftMargin + childLp.rightMargin      //修复：left值更新错误
            }

            //更新top
            top += lineHeight
        }

    }

    //为了能在添加子View时，给子View设置正确的LayoutParams[根据需求来返回]，需要重新几个方法
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams = MarginLayoutParams(p)

    override fun generateDefaultLayoutParams(): LayoutParams = MarginLayoutParams(
        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
    )

    fun setAdapter(adapter: FlowAdapter) {
        println("view === width1 $width")
        println("view === height1 $height")

        removeAllViews()

//        layoutParams = MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        //遍历即将要添加到MarkFlowLayout中的所有子View
        for (position in 0 until adapter.getItemCounts()) {
           var view =  adapter.getView(this, adapter.getItem(position)) as TextView
//            println("view === " + view.text)
//            println("view === 1" + view.width)
//            println("view === 2" + view.height)
//            addView(view)
        }
        //todo 待研究 使用addView添加 自定义控件自身宽高为0
        println("view === width $width")
        println("view === height $height")
//        println("view === 1 ${layoutParams.height}")
//        println("view === 2 ${layoutParams.width}")
    }
}