package com.jack.lib_base.interfac

import android.os.Handler
import java.lang.Runnable
import android.os.SystemClock
import android.os.Looper

/**
 * @创建者 Jack
 * @描述
 */
interface IHandler {
    /**
     * 获取 Handler
     */
    val mHandler: Handler
        get() = HANDLER

    /**
     * 延迟执行
     */
    fun post(runnable: Runnable): Boolean {
        return postDelayed(runnable, 0)
    }

    /**
     * 延迟一段时间执行runnable
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean {
        var delayMillis = delayMillis
        if (delayMillis < 0) {
            delayMillis = 0
        }
        return postAtTime(runnable, SystemClock.uptimeMillis() + delayMillis)
    }

    /**
     * 在指定的时间执行runnable
     */
    fun postAtTime(runnable: Runnable, uptimeMillis: Long): Boolean {
        //发送和当前对象相关的消息回调
        return HANDLER.postAtTime(runnable, this, uptimeMillis)
    }

    /**
     * 移除单个runnable
     */
    fun removeCallbacks(runnable: Runnable) {
        HANDLER.removeCallbacks(runnable)
    }

    /**
     * 移除所有消息回调
     */
    fun removeCallbacks() {
        //移除和当前对象相关的消息回调
        HANDLER.removeCallbacksAndMessages(this)
    }

    companion object {
        val HANDLER = Handler(Looper.getMainLooper())
    }
}