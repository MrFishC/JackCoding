package cn.jack.library_arouter.manager

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @创建者 Jack
 * @创建时间 2021/3/4 17:07
 * @描述
 */
class ArouterU private constructor() {
    companion object {
        @Volatile
        private var instance: ArouterU? = null

        fun getInstance(): ArouterU {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ArouterU()
                    }
                }
            }

            return instance!!
        }
    }

//    fun navigationTo(toPath: String) {
//        ARouter.getInstance().build(toPath)
//            .navigation()
//    }

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