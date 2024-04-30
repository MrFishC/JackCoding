package com.jack.lib_base.uistate.loadsir.weight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import com.jack.lib_base.R

/**
 * @创建者 Jack
 * @创建时间 2022/9/26 17:05
 * @描述
 */
class HwLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mWholeCircleRadius = FloatArray(CIRCLE_COUNT) //记录所有小圆半径
    private val mWholeCircleColors = IntArray(CIRCLE_COUNT) //记录所有小圆颜色
    private var mMaxCircleRadius //小圆最大半径
            = 0f
    private var mSize //控件大小
            = 0
    private var mColor //小圆颜色
            = 0
    private var mPaint //画笔
            : Paint? = null
    private var mAnimator: ValueAnimator? = null
    private var mAnimateValue = 0
    private var mDuration //动画时长
            : Long = 0

    /**
     * 初始化自定义属性
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.HwLoadingView)
        mSize = ta.getDimension(R.styleable.HwLoadingView_hlv_size, dp2px(context, 100f).toFloat())
            .toInt()
        setSize(mSize)
        mColor = ta.getColor(R.styleable.HwLoadingView_hlv_color, Color.parseColor("#333333"))
        setColor(mColor)
        mDuration = ta.getInt(R.styleable.HwLoadingView_hlv_duration, 1500).toLong()
        ta.recycle()
    }

    /**
     * 初始化画笔
     */
    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = mColor
    }

    /**
     * 初始化所有小圆半径、颜色
     */
    private fun initValue() {
        val minCircleRadius = (mSize / 24).toFloat()
        for (i in 0 until CIRCLE_COUNT) {
            when (i) {
                7 -> {
                    mWholeCircleRadius[i] = minCircleRadius * 1.25f
                    mWholeCircleColors[i] = (255 * 0.7f).toInt()
                }
                8 -> {
                    mWholeCircleRadius[i] = minCircleRadius * 1.5f
                    mWholeCircleColors[i] = (255 * 0.8f).toInt()
                }
                9, 11 -> {
                    mWholeCircleRadius[i] = minCircleRadius * 1.75f
                    mWholeCircleColors[i] = (255 * 0.9f).toInt()
                }
                10 -> {
                    mWholeCircleRadius[i] = minCircleRadius * 2f
                    mWholeCircleColors[i] = 255
                }
                else -> {
                    mWholeCircleRadius[i] = minCircleRadius
                    mWholeCircleColors[i] = (255 * 0.5f).toInt()
                }
            }
        }
        mMaxCircleRadius = minCircleRadius * 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //去除wrap_content、Match_Parent对控件的影响
        setMeasuredDimension(mSize, mSize)
    }

    override fun onDraw(canvas: Canvas) {
        if (mSize > 0) {
            //每隔DEGREE_PER_CIRCLE * mAnimateValue角度，绘制所有小圆
            canvas.rotate(
                (DEGREE_PER_CIRCLE * mAnimateValue).toFloat(),
                (mSize / 2).toFloat(),
                (mSize / 2).toFloat()
            )
            for (i in 0 until CIRCLE_COUNT) {
                //设置小圆颜色
                mPaint!!.alpha = mWholeCircleColors[i]
                //每隔DEGREE_PER_CIRCLE角度，绘制一个小圆
                canvas.drawCircle(
                    (mSize / 2).toFloat(),
                    mMaxCircleRadius,
                    mWholeCircleRadius[i],
                    mPaint!!
                )
                canvas.rotate(
                    DEGREE_PER_CIRCLE.toFloat(),
                    (mSize / 2).toFloat(),
                    (mSize / 2).toFloat()
                )
            }
        }
    }

    /**
     * 设置控件大小
     */
    fun setSize(size: Int) {
        mSize = size
        invalidate()
    }

    /**
     * 设置小圆颜色
     */
    fun setColor(@ColorInt color: Int) {
        mColor = color
        invalidate()
    }

    /**
     * 动画监听
     */
    private val mUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
        mAnimateValue = animation.animatedValue as Int
        invalidate()
    }

    /**
     * 开始动画
     */
    fun start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, CIRCLE_COUNT - 1)
            mAnimator!!.addUpdateListener(mUpdateListener)
            mAnimator!!.duration = mDuration
            mAnimator!!.repeatMode = ValueAnimator.RESTART
            mAnimator!!.repeatCount = ValueAnimator.INFINITE
            mAnimator!!.interpolator = LinearInterpolator()
            mAnimator!!.start()
        } else if (!mAnimator!!.isStarted) {
            mAnimator!!.start()
        }
    }

    /**
     * 停止动画
     */
    fun stop() {
        if (mAnimator != null) {
            mAnimator!!.removeUpdateListener(mUpdateListener)
            mAnimator!!.removeAllUpdateListeners()
            mAnimator!!.cancel()
            mAnimator = null
        }
    }

    /**
     * View依附Window时停止动画
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    /**
     * View脱离Window时停止动画
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    /**
     * 根据View可见性变化开始/停止动画
     */
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            start()
        } else {
            stop()
        }
    }

    /**
     * dp转px
     */
    private fun dp2px(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (density * dp + 0.5f).toInt()
    }

    companion object {
        private const val CIRCLE_COUNT = 12 //小圆总数
        private const val DEGREE_PER_CIRCLE = 360 / CIRCLE_COUNT //小圆圆心之间间隔角度差
    }

    init {
        initAttrs(context, attrs)
        initPaint()
        initValue()
    }
}