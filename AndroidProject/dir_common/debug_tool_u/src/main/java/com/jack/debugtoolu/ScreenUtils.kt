package com.jack.debugtoolu

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager

/**
 * @创建者 Jack
 * @创建时间 2024-04-05 11:20
 * @描述
 */
internal object ScreenUtils {
    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度（px）
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.x
    }
}