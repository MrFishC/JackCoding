package com.jack.library_webview.util


/**
 * @创建者 Jack
 * @创建时间 2022/9/21 11:04
 * @描述
 */
open class LogW private constructor() {

    init {
        throw AssertionError()
    }

    companion object {
//        fun init() {
//            if (BuildConfig.DEBUG) {
//                Timber.plant(Timber.DebugTree())
//            }
//        }

        fun d(message: String?) {
            println("LogW " + message)
//            Timber.tag("LogW").d(message)
        }
    }
}