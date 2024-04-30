package com.jack.debugtoolu

import android.app.Application

/**
 * @创建者 Jack
 * @创建时间 2024-04-05 10:59
 * @描述
 */
object DebugManager {
    private var mApplicationContext:Application? = null
    fun init(applicationContext:Application) {
        mApplicationContext = applicationContext
    }

    fun getApplicationContext():Application{
        if(mApplicationContext == null){
            throw RuntimeException("DebugManager's init function must be call frist!")
        }
        return mApplicationContext!!
    }
}