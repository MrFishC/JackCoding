package cn.jack.lib_common.callback

class ResponseCallback<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onFail: (msg: String?) -> Unit = { _ -> }
}