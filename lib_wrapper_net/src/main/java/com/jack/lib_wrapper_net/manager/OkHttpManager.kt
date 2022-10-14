package com.jack.lib_wrapper_net.manager

import okhttp3.OkHttpClient

/**
 * @创建者 Jack
 * @创建时间 2022/8/31 0031 11:46
 * @描述
 */
class OkHttpManager private constructor() {

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = OkHttpManager()
    }

    lateinit var okhttpClient: OkHttpClient

    //将构建OkHttp的配置暴露出去
    fun initClient(okHttpClient: OkHttpClient) {
        this.okhttpClient = okHttpClient ?: OkHttpClient.Builder().build()
    }

}