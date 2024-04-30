package cn.jack.lib_common.flutter.bridge

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 0:04
 * @描述
 */
interface IBridge<P, Callback> {
    fun onBack(p: P?)
    fun goToNative(p: P)
    fun getParams(callback: Callback)
}