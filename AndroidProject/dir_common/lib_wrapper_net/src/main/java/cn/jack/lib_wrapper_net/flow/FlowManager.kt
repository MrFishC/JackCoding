package cn.jack.lib_wrapper_net.flow

import com.jack.lib_wrapper_net.model.ApiResponse
import com.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 21:35
 * @描述
 *
 */
object FlowManager {
    /**
     * 两种调用方法根据实际需求进行选择
     *
     * eg:
    fun register(userName: String,passwd: String,againPasswd: String) =
        FlowManager.httpRequest<UserInfo> {
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .register(userName, passwd, againPasswd)
                .map {
                    EventResult.OnNext(it.data)
                }
            }
     */
  inline fun <T> httpRequest(requestBlock: () -> Flow<EventResult<T>>) =
        requestBlock()
            //flowOn:仅限于其上游,与其位置强相关
            .flowOn(Dispatchers.IO)
            //catch的作用域仅限于上游      对发生在上游，中间操作符异常的处理,未捕获的异常在消费时抛出
            //总结flow异常的处理方案，注意catch的作用域  下游使用try catch
            .catch { e ->
                emit(EventResult.OnError(e))        //emit：挂起函数，往下游发送数据         上游创建数据，同时也要将数据发送出去
            }
            .onStart {
                emit(EventResult.OnStart)
            }.onCompletion {
                emit(EventResult.OnComplete)
            }

    /**
     *
     * eg:
        fun register(
        userName: String,
        passwd: String,
        againPasswd: String
        ) = FlowManager.httpRequestSimple<UserInfo>(
                HttpManager.obtainRetrofitService(ApiService::class.java)
                .register(userName, passwd, againPasswd)
            )
     */
    fun <T> httpRequestSimple(
        parameters: Flow<ApiResponse<T>>,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<EventResult<T>> {
        val transform: Flow<EventResult<T>> = parameters
            .map {
                /*数据剥壳，同时可以根据不同的状态来处理异常问题[或通过网络拦截器处理]*/
                if (it.errorCode == 0) {
                    EventResult.OnNext(it.data)
                } else {
                    EventResult.OnFail(Throwable(it.errorMsg))
                }
            }

        return transform.flowOn(coroutineDispatcher)
            .catch { e ->
                emit(EventResult.OnError(e))
            }
            .onStart {
                emit(EventResult.OnStart)
            }
            .onCompletion {
                emit(EventResult.OnComplete)
            }
    }
}

