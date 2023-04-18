package cn.jack.library_util

import android.app.Application
import android.content.Context
import android.os.Build
import android.view.Display
import android.view.WindowManager

/**
 * Created by jack
 * on 2017/12/12.
 *
 * 改进使用单例
 */
class DensityU {

    companion object {
        private var context: Application? = null
        fun init(appContext: Application) {
            context = appContext
        }

        /**
         * @param context 使用全局的上下文,防止内存泄漏  同时若使用kotlin，上下文使用不当，容易产生NPE
         * @param pxValue
         * @return
         */
        fun px2sp(pxValue: Float): Int {
            if (context == null) throw IllegalStateException("Please call DensityU init function first.")
            val fontScale = context!!.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        fun sp2px(spValue: Float): Int {
            if (context == null) throw IllegalStateException("Please call DensityU init function first.")
            val fontScale = context!!.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        fun dip2px(dipValue: Float): Int {
            if (context == null) throw IllegalStateException("Please call DensityU init function first.")
            val scale = context!!.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        fun px2dip(pxValue: Float): Int {
            if (context == null) throw IllegalStateException("Please call DensityU init function first.")
            val scale = context!!.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun getScreenWidth(): Int {
            if (context == null) throw IllegalStateException("Please call DensityU init function first.")
            val manager: WindowManager =
                context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val display: Display = context!!.display!!
                display.width
            } else {
                val display: Display = manager.getDefaultDisplay()
                display.width
            }
        }

    }


//    /**                 todo 存在疑惑 这样做效果就不同了
//     * 更好的适配
//     * @param dimen 合适的dimen文件下的值
//     * @return
//     */
//    fun getDimenValue(dimen: Int): Int {
//        return dip2px(
//            ContextU.context(),
//            ContextU.context().getResources().getDimension(dimen)
//        )
//    }
}