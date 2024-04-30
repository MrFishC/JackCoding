package com.jack.lib_wrapper_mvvm.mvvm.model
import com.jack.lib_wrapper_mvvm.interfa.IModel

/**
 * @创建者 Jack
 * @创建时间 2022/6/28 16:27
 * @描述
 */
open class BaseWrapperModel : IModel {
    override fun onCleared() {
        /*现在使用kotlin和flow，这里几乎用不上，如果使用java和rxjava，这里的逻辑可以参考*/
    }
}