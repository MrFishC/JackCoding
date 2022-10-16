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
     * 是否使用沉寂式状态栏
     */
    fun isStatusBar(): Boolean = true

    /**
     * 状态栏背景颜色默认为透明
     */
    fun isTransparent(): Boolean = true


    /**
     * 状态栏字体是深色，不写默认为亮色
     */
    fun isDefaultStatusBar(): Boolean = true

    /**
     * 是否使用Loadsir-针对整个页面
     */
    fun isRegisterLoadSir(): Boolean = false

}