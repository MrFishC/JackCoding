package com.jack.lib_wrapper_mvvm.interfa

/**
 * @创建者 Jack
 * @创建时间 2022/6/28 17:02
 * @描述 服务于顶层View的接口
 */
interface IBaseView {
    /**
     * 初始化参数
     */
    fun prepareParam()

    /**
     * 初始化数据
     */
    fun prepareData()

    /**
     * 初始化监听
     */
    fun prepareListener()
}