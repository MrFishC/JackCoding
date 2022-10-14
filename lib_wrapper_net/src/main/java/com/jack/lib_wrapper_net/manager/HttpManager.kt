package com.jack.lib_wrapper_net.manager

import com.coder.vincent.sharp_retrofit.call_adapter.flow.FlowCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * created by Jack
 * date:19-5-5
 * describe:
 *
 */
object HttpManager {

    private const val BASE_URL = "https://www.wanandroid.com/"

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpManager.instance.okhttpClient)
        .addCallAdapterFactory(FlowCallAdapterFactory.create())  // 添加Flow适配器
        .addConverterFactory(GsonConverterFactory.create())         // 添加Gson转换器
        .build()

    /*根据传入的 Class 获取对应的 Retrofit service*/
    fun <T> obtainRetrofitService(service: Class<T>): T {
        return retrofitClient.create(service)
    }
}