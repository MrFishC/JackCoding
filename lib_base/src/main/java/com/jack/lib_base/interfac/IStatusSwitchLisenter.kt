package com.jack.lib_base.interfac

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:47
 * @描述
 */
interface IStatusSwitchLisenter {

    /**
     * 默认不Inject
     * 使用ARouter传递参数需要重写该方法，设置返回值为true
     */
    fun injectARouter(): Boolean {
        return true
    }


    /**
     * 是否注册EventBus，默认不注册
     */
    fun isRegisterEventBus(): Boolean {
        return false
    }

    /**
     * 状态栏默认为黑色
     * @return
     */
    fun isBlack(): Boolean {
        return true
    }

    fun isDefaultStatusBar(): Boolean {
        return true
    }

    /**
     * 是否使用Loadsir
     */
    fun isRegisterLoadSir(): Boolean {
        return false
    }

    /**
     * 指定的View是否使用Loadsir
     */
    fun isViewRegisterLoadSir(): Boolean {
        return false
    }
}