package com.jack.lib_base.interfac

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:47
 * @描述
 */
interface IStatusSwitchLisenter {

    /**
     * 默认使用Inject
     */
    fun injectARouter(): Boolean = true

    /**
     * 是否注册EventBus，默认不注册
     */
    fun isRegisterEventBus(): Boolean = false

    /**
     * 状态栏默认为黑色
     * @return
     */
    fun isBlack(): Boolean = true

    fun isDefaultStatusBar(): Boolean = true

    /**
     * 是否使用Loadsir-针对整个页面
     */
    fun isRegisterLoadSir(): Boolean = false

}