package cn.jack.library_arouter.manager

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @创建者 Jack
 * @创建时间 2021/3/4 17:07
 * @描述
 */
class ArouterManager private constructor() {
    companion object {
        @Volatile
        private var instance: ArouterManager? = null

        fun getInstance(): ArouterManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ArouterManager()
                    }
                }
            }

            return instance!!
        }
    }

    fun navigationTo(toPath: String, bundle: Bundle? = null) {
        if (bundle != null) {
            ARouter.getInstance().build(toPath)
                .with(bundle)
                .navigation()
        } else {
            ARouter.getInstance().build(toPath)
                .navigation()
        }
    }
}