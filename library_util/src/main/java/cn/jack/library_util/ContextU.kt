package cn.jack.library_util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * created by Jack
 * date:19-4-27
 * describe:全局的context
 */
class ContextU {
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var sContext: Context
        private lateinit var sApplication: Application

        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(context: Context, application: Application) {
            sContext = context.applicationContext
            sApplication = application
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun context(): Context = sContext

        /**
         * 获取Application
         *
         */
        fun application(): Application = sApplication
    }
}