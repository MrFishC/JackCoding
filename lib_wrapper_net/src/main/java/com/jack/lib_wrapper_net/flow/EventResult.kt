package com.jack.lib_wrapper_net.flow

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 21:36
 * @描述
 */
sealed class EventResult<out R> {
    object OnStart : EventResult<Nothing>()
    object OnComplete : EventResult<Nothing>()
    data class OnNext<out T>(val data: T?) : EventResult<T>()
    object OnEmpty: EventResult<Nothing>()
    data class OnError(val throwable: Throwable) : EventResult<Nothing>()
}