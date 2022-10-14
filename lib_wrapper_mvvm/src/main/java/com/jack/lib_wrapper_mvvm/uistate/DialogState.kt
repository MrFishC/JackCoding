package com.jack.lib_wrapper_mvvm.uistate

/**
 * @创建者 Jack
 * @创建时间 2022/8/26 0026 15:14
 * @描述
 */
sealed class DialogState {
    object OnHide : DialogState()
    object OnLoading : DialogState()
}