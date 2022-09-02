package com.jack.lib_wrapper_net.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @创建者 Jack
 * @创建时间 2022/8/28 0028 14:08
 * @描述
 */
object RequestManager {
    private const val BASE_URL = "https://www.wanandroid.com/"

    val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
//                .addInterceptor(TokenInterceptor())
//                .cookieJar()
                .build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}