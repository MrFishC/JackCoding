package com.jack.lib_wrapper_mvvm.ext

import android.view.View

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 20:50
 * @描述
 */
class SingleClickListener(private val delayMillis: Long = 500, private val listener: View.OnClickListener) : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > delayMillis) {
            lastClickTime = currentTime
            listener.onClick(v)
        }
    }

}