package cn.jack.lib_common.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.callback.ResponseCallback
import cn.jack.lib_base.ext.closeDialog
import cn.jack.lib_base.ext.loadDialog
import cn.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * 使用 协程作用域 + 拓展函数 + flow + 函数类型 进一步封装，简化View层代码
 */
fun <T> AppCompatActivity.observeInResult(
    sharedFlow: SharedFlow<EventResult<T>>,
    defaultDialog: Boolean = true,
    listenerResponse: ResponseCallback<T>.() -> Unit
) {
    //可重启生命周期感知型协程 https://developer.android.com/topic/libraries/architecture/coroutines#restart
    lifecycleScope.launch {
        //StateFlow和SharedFlow是热流，热流不会随着生命周期自动取消，也就是说页面消失后还会继续监听数据，或者下次进入时候会重复绑定，这样就会导致很多无法预料的错误。
        //观察StateFlow需要在协程中，一般我们会使用下面几种
        //1、lifecycleScope.launch: 立即启动协程，并且在本 Activity或Fragment 销毁时结束协程。
        //2、LaunchWhenStarted 和 LaunchWhenResumed:它会在lifecycleOwner进入X状态之前一直等待，又在离开X状态时挂起协程
        //StateFlow 或任意其他数据流收集数据的操作并不会停止，所以官方推荐repeatOnLifecycle来构建协程。
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect {
                val callback = ResponseCallback<T>().also(listenerResponse)
                when (it) {
                    is EventResult.OnStart -> {
                        if (defaultDialog) {
                            loadDialog()
                        } else {
                            callback.onStart()
                        }
                    }

                    is EventResult.OnNext -> {
                        if (defaultDialog) {
                            closeDialog()
                        }
                        callback.onSuccess(it.data)
                    }

                    is EventResult.OnFail -> {
                        if (defaultDialog) {
                            closeDialog()
                            showToast(it.throwable.message)
                        } else {
                            callback.onFail(it.throwable.message)
                        }
                    }

                    is EventResult.OnError -> {
                        if (defaultDialog) {
                            closeDialog()
                            showToast(it.throwable.message)
                        } else {
                            callback.onError(it.throwable.message)
                        }
                    }

                    is EventResult.OnComplete -> {
                        if (defaultDialog) {
                            closeDialog()
                        } else {
                            callback.onComplete()
                        }
                    }
                }
            }
        }
    }
}

fun <T> Fragment.observeInResult(
    sharedFlow: SharedFlow<EventResult<T>>,
    defaultDialog: Boolean = true,
    listenerResponse: ResponseCallback<T>.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            sharedFlow.collect {
                val callback = ResponseCallback<T>().also(listenerResponse)
                when (it) {
                    is EventResult.OnStart -> {
                        if (defaultDialog) {
                            loadDialog()
                        } else {
                            callback.onStart()
                        }
                    }

                    is EventResult.OnNext -> {
                        if (defaultDialog) {
                            closeDialog()
                        }
                        callback.onSuccess(it.data)
                    }

                    is EventResult.OnFail -> {
                        if (defaultDialog) {
                            closeDialog()
                            showToast(it.throwable.message)
                        } else {
                            callback.onFail(it.throwable.message)
                        }
                    }

                    is EventResult.OnError -> {
                        if (defaultDialog) {
                            closeDialog()
                            showToast(it.throwable.message)
                        } else {
                            callback.onError(it.throwable.message)
                        }
                    }

                    is EventResult.OnComplete -> {
                        if (defaultDialog) {
                            closeDialog()
                        } else {
                            callback.onComplete()
                        }
                    }
                }
            }
        }
    }
}
