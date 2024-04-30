package cn.jack.lib_common.callback

class ResponseCallback<T> {
    var onStart: () -> Unit = {}
//    var onEmpty: () -> Unit = {}      //数据空的情况还是不单独抽离出来，自身在onSuccess方法中根据业务去处理 因为不同的后台定义的bean数据会存在差异
    var onSuccess: (data: T?) -> Unit = {}
    var onFail: (msg: String?) -> Unit = {}
    var onError: (msg: String?) -> Unit = {}
    var onComplete: () -> Unit = {}
}