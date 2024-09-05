package cn.jack.lib_common.interceptor

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
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url = request.url.toString()
        //判断是否是登录接口，若是则将cookie信息获取并保存起来，后续要提供给flutter端使用
        if (isLogin(url)) {
            //判断是否是登录接口，若是则将cookie信息获取并保存起来，后续要提供给flutter端使用
            val response = chain.proceed(request)
            val cookie = response.headers["set-cookie"]
            val session = response.headers["cookie"]
            println("获取 cookie $cookie")
            println("获取 session $session")
            if (cookie?.isNotEmpty() == true) {
                KvStoreUtil.getInstance().save(C.Login.cookie, cookie)
            }
            //HTTP FAILED: java.lang.IllegalStateException: cannot make a new request because the previous response is still open
            response.close()//需要调用一下，否则会出现报错
        }
        //wanandroid的api，只需要携带cookie即可，实际项目中采用TOKEN的方式较多
//        else if (needAddToken(url)) {
//            val token = KvStoreUtil.getInstance().getString(C.Login.user_token) + ""
//            val updateRequest = request.newBuilder().header(TOKEN, token).build()
//            return chain.proceed(updateRequest)
//        }
        return chain.proceed(request)
    }

    /**
     * 不需要添加token的api
     */
    private fun needAddToken(url: String): Boolean {
        return !url.contains("../...") || !url.contains("../...")
    }

    /**
     * 过滤出登录接口，根据实际需求进行调整
     */
    private fun isLogin(url: String): Boolean {
        return url.contains("user/login")
    }
}