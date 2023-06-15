package cn.jack.lib_common.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.inter.ResponseCallback
import com.jack.lib_base.ext.closeDialog
import com.jack.lib_base.ext.loadDialog
import com.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * 使用 携程作用域 + 拓展函数 + flow + 函数类型 进一步封装，简化View层代码
 */
fun <T> AppCompatActivity.parseResponseWithCallback(
    sharedFlow: SharedFlow<EventResult<T>>,
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
                        loadDialog()
                    }

                    is EventResult.OnNext -> {
                        closeDialog()
                        callback.onSuccess(it.data)
                    }

                    is EventResult.OnFail -> {
                        closeDialog()
                        showToast(it.throwable.message)
                        callback.onFail(it.throwable.message)
                    }

                    is EventResult.OnError -> {
                        closeDialog()
                        showToast(it.throwable.message)
                    }

                    is EventResult.OnComplete -> {
                        closeDialog()
                    }
                }
            }
        }
    }
}
