package com.jack.lib_wrapper_mvvm.ext

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/*通用的拓展函数可以单独写在一个lib中进行单独的管理*/
fun Activity.setStatusBarTranslucent(navigationBarColor: Int = Color.BLACK) {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        var flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 设置状态栏文字为深色
        }
        decorView.systemUiVisibility = flag
        statusBarColor = Color.TRANSPARENT
        this.navigationBarColor = navigationBarColor
    }
}

fun Activity.finisActivity() {
    finish()
}
