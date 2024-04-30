package com.jack.lib_base.uistate

/**
 * @创建者 Jack
 * @创建时间 2022/8/26 0026 15:14
 * @描述
 */
sealed class LayoutState {
    object OnLoading : LayoutState()
    object OnFailed : LayoutState()
    object OnEmpty : LayoutState()
    object OnTimeout : LayoutState()
    object OnCustom : LayoutState()
    object OnNetError : LayoutState()
    object OnSuccess : LayoutState()
}