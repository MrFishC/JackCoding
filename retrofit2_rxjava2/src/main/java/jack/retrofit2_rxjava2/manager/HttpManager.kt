package jack.retrofit2_rxjava2.manager

import com.coder.vincent.sharp_retrofit.call_adapter.flow.FlowCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * created by Jack
 * date:19-5-5
 * describe:
 *  待实战 自定义转换器  fastjson flow
 *
 *  flow的转换器 https://blog.csdn.net/jungle_pig/article/details/105725160
 *
 *
 *  https://blog.csdn.net/parade0393/article/details/106254291/
 */
object HttpManager {

    private const val BASE_URL = "https://www.wanandroid.com/"

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpManager.instance.okhttpClient)
        .addCallAdapterFactory(FlowCallAdapterFactory.create())  // 添加Flow适配器
        .addConverterFactory(GsonConverterFactory.create())         // 添加Gson转换器
        .build()

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
    </T> */
    fun <T> obtainRetrofitService(service: Class<T>): T {
        return retrofitClient.create(service)
    }
}