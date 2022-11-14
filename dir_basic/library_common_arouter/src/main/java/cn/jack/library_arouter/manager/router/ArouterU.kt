package cn.jack.library_arouter.manager.router

import android.os.Bundle
import cn.jack.library_util.ContextU
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
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

    /**
     * bundle：参数的传递，可以实现Arouter跨进程的调用
     */
    fun navigationTo(
        toPath: String,
        bundle: Bundle? = null,
    ) {
        if (bundle != null) {
            ARouter.getInstance().build(toPath)
                .with(bundle)
                .navigation()
        } else {
            ARouter.getInstance().build(toPath)
                .navigation()
        }
    }

    fun navigationToWithIntercept(
        toPath: String,
        bundle: Bundle? = null,
    ) {
        if (bundle != null) {
            ARouter.getInstance().build(toPath)
                .with(bundle)
                .navigation(ContextU.context(), object : NavCallback() {
                    override fun onArrival(postcard: Postcard) {
                        println(ArouterU::class.java.name + " navigationToWithIntercept onArrival " + postcard)
                    }

                    override fun onInterrupt(postcard: Postcard) {
                        println(ArouterU::class.java.name + " navigationToWithIntercept onInterrupt " + postcard)
                    }
                })
        } else {
            ARouter.getInstance().build(toPath)
                .navigation(ContextU.context(), object : NavCallback() {
                    override fun onArrival(postcard: Postcard) {
                        println(ArouterU::class.java.name + " navigationToWithIntercept2 onArrival " + postcard)
                    }

                    override fun onInterrupt(postcard: Postcard) {
                        println(ArouterU::class.java.name + " navigationToWithIntercept2 onInterrupt " + postcard)
                    }
                })
        }
    }

    fun navigationToWithInterceptCallback(
        toPath: String,
        bundle: Bundle? = null,
        interceptCallback: InterceptCallback
    ) {
        if (bundle != null) {
            ARouter.getInstance().build(toPath)
                .with(bundle)
                .navigation(ContextU.context(), object : NavCallback() {
                    override fun onArrival(postcard: Postcard) {
                        interceptCallback.arrival()
                    }

                    override fun onInterrupt(postcard: Postcard) {
                        interceptCallback.interrupt()
                    }
                })
        } else {
            ARouter.getInstance().build(toPath)
                .navigation(ContextU.context(), object : NavCallback() {
                    override fun onArrival(postcard: Postcard) {
                        interceptCallback.arrival()
                    }

                    override fun onInterrupt(postcard: Postcard) {
                        interceptCallback.interrupt()
                    }
                })
        }
    }

    interface InterceptCallback {
        fun arrival()
        fun interrupt()
    }
}