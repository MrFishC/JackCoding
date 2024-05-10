package cn.jack.simple02_custom_view.debug

import cn.jack.lib_common.CustomApplication
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 12:29
 * @描述 每个业务模块使用
 */
//@HiltAndroidApp
class DebugApplication : CustomApplication() {
    override fun initArouter() {
        if (isDebugMode()) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}