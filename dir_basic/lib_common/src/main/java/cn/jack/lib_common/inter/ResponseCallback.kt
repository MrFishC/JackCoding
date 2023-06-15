package cn.jack.lib_common.inter

class ResponseCallback<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onFail: (msg: String?) -> Unit = { _ -> }
}