package com.jack.lib_base.ext

import com.jack.library_webview.util.MainLooper
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService

/**
 * @创建者 Jack
 * @创建时间 2022/9/25 14:42
 * @描述
 */

fun LoadService<Any>.postCallbackDelayed(callback: Class<out Callback>, delayedTime: Long = 1000) {
    MainLooper.getInstance().postDelayed(Runnable {
        this.showCallback(callback)
    }, delayedTime)
}

fun LoadService<Any>.postSuccessDelayed(delayedTime: Long = 1000) {
    MainLooper.getInstance().postDelayed(Runnable {
        this.showSuccess()
    }, delayedTime)
}