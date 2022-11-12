package cn.jack.library_util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 17:41
 * @描述
 */
class LogU private constructor() {

    companion object {
        private var LOGGER_TAG = LogU::class.java.name
        private var mIsDebug = false

        fun init(debug: Boolean, logTag: String) {
            mIsDebug = debug
            LOGGER_TAG = logTag
            Logger.addLogAdapter(AndroidLogAdapter())
        }

        fun d(message: Any?) {
            d(LOGGER_TAG, message)
        }

        fun i(message: String?) {
            i(LOGGER_TAG, message)
        }

        fun w(message: String?) {
            w(LOGGER_TAG, message)
        }

        fun e(message: String?) {
            e(LOGGER_TAG, message)
        }

        fun d(tag: String, message: Any?) {
            if (mIsDebug) {
                Logger.t(tag).d(message)
            }
        }

        fun i(tag: String, message: String?) {
            if (mIsDebug) {
                if (message != null) {
                    Logger.t(tag).i(message)
                } else {
                    Logger.t(tag).i("数据为null")
                }
            }
        }

        fun w(tag: String, message: String?) {
            if (mIsDebug) {
                if (message != null) {
                    Logger.t(tag).w(message)
                } else {
                    Logger.t(tag).w("数据为null")
                }
            }
        }

        fun e(tag: String, message: String?) {
            if (mIsDebug) {
                if (message != null) {
                    Logger.t(tag).e(message)
                } else {
                    Logger.t(tag).e("数据为null")
                }
            }
        }
    }
}