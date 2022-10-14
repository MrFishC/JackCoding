package cn.jackmvvm

import cn.jack.lib_common.CustomApplication
import com.alibaba.android.arouter.launcher.ARouter
import dagger.hilt.android.HiltAndroidApp

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 12:29
 * @描述
 */
@HiltAndroidApp
class AppApplication : CustomApplication() {

    override fun initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

}