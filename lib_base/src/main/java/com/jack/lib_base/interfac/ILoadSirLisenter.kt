package com.jack.lib_base.interfac

/**
 * @创建者 Jack
 * @描述
 */
interface ILoadSirLisenter {
    //fragment和activity都可以使用，复用性更高
    //使用loadsir需要重写该方法
    fun dataReload() {}
}