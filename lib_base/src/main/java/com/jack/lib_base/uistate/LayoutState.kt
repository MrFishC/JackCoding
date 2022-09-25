package com.jack.lib_base.uistate

/**
 * @创建者 Jack
 * @创建时间 2022/8/26 0026 15:14
 * @描述
 */
sealed class LayoutState<out R> {
    object onLoading : LayoutState<Nothing>()
    object onFailed : LayoutState<Nothing>()
    object onEmpty : LayoutState<Nothing>()
    object onTimeout : LayoutState<Nothing>()
    object onCustom : LayoutState<Nothing>()
}