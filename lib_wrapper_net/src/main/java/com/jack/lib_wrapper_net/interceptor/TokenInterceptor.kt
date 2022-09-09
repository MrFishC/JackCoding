package com.jack.lib_wrapper_net.interceptor

import cn.jack.library_common_business.constant.C
import cn.jack.library_util.KvStoreUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * created by Jack
 * describe:网络请求token的统一添加
 *
 * 拦截器的多种用法
 * https://www.jianshu.com/p/eaee7cd227cd
 */
class TokenInterceptor : Interceptor {
    private val TOKEN = "token"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url = request.url.toString()

        if (needAddToken(url)) {
            val token = KvStoreUtil.getInstance()?.getString(C.Login.user_token) + ""
            val updateRequest = request.newBuilder().header(TOKEN, token).build()
            return chain.proceed(updateRequest)
        }
        return chain.proceed(request)
    }

    /**
     * 不需要添加token的api
     */
    private fun needAddToken(url: String): Boolean {
        return !url.contains("../...") || !url.contains("../...")
    }
}