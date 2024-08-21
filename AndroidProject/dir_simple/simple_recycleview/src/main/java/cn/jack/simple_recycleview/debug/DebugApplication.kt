package cn.jack.simple_recycleview.debug

import cn.jack.lib_common.CustomApplication
import cn.jack.simple_recycleview.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 12:29
 * @描述 每个业务模块使用
 */
//@HiltAndroidApp
class DebugApplication : CustomApplication() {
    override fun initArouter() {
        if (cn.jack.simple_recycleview.BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}