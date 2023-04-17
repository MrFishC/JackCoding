package cn.jack.module_fragment_04

import android.content.Context
import android.view.Display
import android.view.WindowManager
import cn.jack.library_util.ContextU

/**
 * Created by jack
 * on 2017/12/12.
 *
 * 改进使用单例
 */
object DensityTool {
    /**
     * @param context 使用全局的上下文,防止内存泄漏
     * @param pxValue
     * @return
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * @param context 使用全局的上下文,防止内存泄漏
     * @param spValue
     * @return
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     *
     * @param context 使用全局的上下文,防止内存泄漏
     * @param dipValue
     * @return
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     *
     * @param context 使用全局的上下文,防止内存泄漏
     * @param pxValue
     * @return
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * @param context 使用全局的上下文,防止内存泄漏
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val manager: WindowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = manager.getDefaultDisplay()
        return display.width
    }

    /**                 todo 存在疑惑 这样做效果就不同了
     * 更好的适配
     * @param dimen 合适的dimen文件下的值
     * @return
     */
    fun getDimenValue(dimen: Int): Int {
        return dip2px(
            ContextU.context(),
            ContextU.context().getResources().getDimension(dimen)
        )
    }
}