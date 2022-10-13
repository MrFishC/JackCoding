package cn.jack.module_login.debug

import cn.jack.module_login.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.base.view.BaseApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 12:29
 * @描述 每个业务模块使用
 */
@HiltAndroidApp
class DebugApplication : BaseApplication() {

    override fun initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

}