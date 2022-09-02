package jack.retrofit2_rxjava2.manager.rx

import io.reactivex.functions.Function
import jack.retrofit2_rxjava2.util.net.NetConfig
import jack.retrofit2_rxjava2.exception.ApiException
import jack.retrofit2_rxjava2.exception.UnloginException
import jack.retrofit2_rxjava2.exception.DataNullException
import jack.retrofit2_rxjava2.exception.TimeOutException
import jack.retrofit2_rxjava2.model.ApiResponse
import java.io.IOException

/**
 * created by Jack
 * date:19-5-5
 * describe:将ApiResponse<T>转换成T格式
 *
 * 数据剥壳：
 * 同时对stat的不同状态进行处理
 * 执行rxfuntion的前提，是后台能返回apiResponse的数据
</T> */
class RxFunction<T> : Function<ApiResponse<T>, T> {
    @Throws(IOException::class)
    override fun apply(apiResponse: ApiResponse<T>): T {
        val status = apiResponse.errorCode
        return if (status == NetConfig.CODE_ERROR || status == NetConfig.NOT_MATCH) {
            throw ApiException(apiResponse.errorCode, apiResponse.errorMsg)
        } else if (status == NetConfig.UN_LOGIN) {
            throw UnloginException(apiResponse.errorCode, apiResponse.errorMsg)
        } else if (status == NetConfig.CODE_SUCCESS) {
            if (apiResponse.data == null) {
                //这里通过自定义转换器 对 返回的数据为null的情况 做一下处理
                throw DataNullException(apiResponse.errorCode, apiResponse.errorMsg)
            }
            apiResponse.data
        } else {
            throw TimeOutException(apiResponse.errorCode, "请求超时")
        }
    }
}