package com.jack.lib_base.util

/**
 * @创建者 Jack
 * @创建时间 2023/4/22
 * @描述
 */
class DoubleCU {
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        private var lastClickTime: Long = 0
        private var TIME: Long = 800

        fun init(time: Long) {
            TIME = time
        }

        val isFastDoubleClick: Boolean
            get() {
                val time = System.currentTimeMillis()
                if (time - lastClickTime < TIME) {
                    return true
                }
                lastClickTime = time
                return false
            }
    }
}