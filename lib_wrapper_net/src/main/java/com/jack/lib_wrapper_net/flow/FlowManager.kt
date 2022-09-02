package com.jack.lib_wrapper_net.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 21:35
 * @描述
 *
 */
open class FlowManager<T> {
    fun httpRequest(requestBlock: () -> Flow<EventResult<T>>) =
        requestBlock().flowOn(Dispatchers.IO)
            .catch { e -> emit(EventResult.OnError(e)) }
            .onStart {
                emit(EventResult.OnStart)
            }.onCompletion {
                emit(EventResult.OnComplete)
            }
}

