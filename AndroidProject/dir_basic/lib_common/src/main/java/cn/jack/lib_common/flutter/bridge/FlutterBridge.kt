package cn.jack.lib_common.flutter.bridge

import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_util.KvStoreUtil
import cn.jack.library_util.LogU
import cn.jack.library_util.ToastU
import com.alibaba.android.arouter.launcher.ARouter
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 0:20
 * @描述
 * 该类的初始化时机需要注意
 */
class FlutterBridge : MethodChannel.MethodCallHandler,
    IBridge<Any?, MethodChannel.Result?> {

    //由于有多个FlutterEngine，每个FlutterEngine需要单独注册一个MethodChannel
    private var mMethodChannels = mutableListOf<MethodChannel>()

    companion object {
        @JvmStatic
        var instance: FlutterBridge? = null
            private set

        @JvmStatic
        fun init(flutterEngine: FlutterEngine): FlutterBridge? {
            val methodChannel = MethodChannel(
                flutterEngine.dartExecutor,
                "FlutterBridge"//该名称要和flutter端定义的名字要对应
            )
            if (instance == null) {
                FlutterBridge().also { instance = it }
            }
            //调用 setMethodCallHandler 方法 , 可以为 MethodChannel 设置一个 方法回调处理器 ;
            methodChannel.setMethodCallHandler(instance)
            instance!!.apply {
                mMethodChannels.add(methodChannel)
            }
            return instance
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        //处理来自Flutter端的方法调用
        //call: MethodCall， Dart 端传递来的消息 ;
        //result: MethodChannel.Result， 向 Dart 端回传的数据 ;
        LogU.d("来自Flutter端的信息 ${call.method}")
        when (call.method) {
            "onBack" -> onBack(call.arguments)
            "getHeaderParams" -> getParams(result)
            "goToNative" -> goToNative(call.arguments)
            else -> result.notImplemented()
        }
        //具体的参数解析
//     var name =   call.argument<String>("name")
        //回传给dart端
//        result.success()
    }

    override fun onBack(p: Any?) {
//        if (ActivityManager.instance.getTopActivity(true) is HiFlutterActivity) {
//            (ActivityManager.instance.getTopActivity(true) as HiFlutterActivity).onBackPressed()
//        }
    }

    override fun goToNative(p: Any?) {
        if (p is Map<*, *>) {
            val action = p["action"]//根据约定获取参数，根据不同的参数指定相应的业务逻辑
            if (action == "goToDetail") {
                val goodsId = p["goodsId"]
//                ARouter.getInstance().build("/detail/main").withString(
//                    "goodsId",
//                    goodsId as String?
//                ).navigation()
            } else if (action == "goToLogin") {
                LogU.e("token失效了啊 清除token，重新登录！")
                KvStoreUtil.getInstance().clearAll()
                ToastU.normal("token失效，请重新登录")
                ARouter.getInstance().build(RouterPathActivity.Login.PAGER_LOGIN).navigation()
            }
        }
    }

    override fun getParams(callback: MethodChannel.Result?) {
        //可以通过callback返回结果给Dart
        callback?.success("Android收到了 Flutter--->Android的消息，这次是回复操作")
    }

    //native发送给flutter端
    //method:在flutter页面注册
    fun native2Flutter(method: String, arguments: Any? = null) {
        mMethodChannels.forEach {
            //通过invokeMethod将数据传递给flutter端
            it.invokeMethod(method, arguments)
        }
    }

    fun native2Flutter(method: String, arguments: Any? = null, callback: MethodChannel.Result?) {
        mMethodChannels.forEach {
            //native向flutter传递数据后，flutter端可以向native回复本次数据的
            LogU.d("native2Flutter method $method")
            LogU.d("native2Flutter arguments $arguments")
            LogU.d("native2Flutter callback $callback")
            it.invokeMethod(method, arguments, callback)
        }
    }
}