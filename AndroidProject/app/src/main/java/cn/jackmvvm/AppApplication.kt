package cn.jackmvvm

import cn.jack.lib_common.CustomApplication
import com.jack.module_hybird.MultipleModulesNameProvider
import cn.jack.lib_common.flutter.cache.FlutterCacheManager
import cn.jack.library_util.ActivityManager
import cn.jack.library_util.ToastU
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

    override fun initOthers() {
        super.initOthers()
        initActivityManager()

        FlutterCacheManager.instance?.preLoad(this, MultipleModulesNameProvider())
    }

    private fun initActivityManager() {
        ActivityManager.instance.init(this)
        ActivityManager.instance.addFrontBackCallback(object : ActivityManager.FrontBackCallback {
            override fun onChanged(front: Boolean) {
                ToastU.normal("当前处于 $front")
            }
        })
    }

}