package cn.jack.module_fragment_05

import android.os.Bundle
import android.view.View
import cn.jack.lib_common.flutter.base.BaseFlutterFragment
import cn.jack.lib_common.flutter.bridge.FlutterBridge
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.library_common_business.constant.C
import cn.jack.library_util.KvStoreUtil
import cn.jack.library_util.ToastU
import com.alibaba.android.arouter.facade.annotation.Route
import cn.jack.module_hybird.MultipleModulesNameProvider
import io.flutter.plugin.common.MethodChannel

/**
 * 使用flutter实现
 */
@Route(path = RouterPathFragment.HomeFive.PAGER_HOME_FIVE)
class ModuleFragment05 : BaseFlutterFragment(MultipleModulesNameProvider.MODULE_NAME_MINE) {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        sendMessageToFlutter()
//    }

    override fun loadData() {
        super.loadData()
        //页面可见时再发送消息，但是接收消息的flutter页面的initState回调已经触发。这个猜测是因为：作为主页的5个fragment中的其中一个的原因导致的。
        sendMessageToFlutter()
    }

    private fun sendMessageToFlutter(){
        val cookie = KvStoreUtil.getInstance().getString(C.Login.cookie) + ""
        println("参数信息内容查看一下 cookie $cookie")
        //native发消息给flutter，传递token。Flutter端收到之后进行保存；
        FlutterBridge.instance?.native2Flutter(
            MultipleModulesNameProvider.POST_COOKIE,
            cookie,
            object : MethodChannel.Result {
                override fun success(result: Any?) {
                    println("successxxxxxxxx ${result}")
                    //表示调用成功 ;
                    if (result is String) {
                        val status = result as String
                        println("status ${status}")
                        when (status) {
                            "onSuccess" -> {
                                println("onSuccess 业务逻辑")
                                FlutterBridge.instance?.native2Flutter(MultipleModulesNameProvider.REFRESH)
                            }

                            "onFailed" -> {
                                println("onFailed 业务逻辑")
                            }
                        }
//                        ToastU.normal(result)
                    }
                }

                override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
                    // 表示出现错误 ;
//                    ToastU.normal(errorMessage!!)

                    println("MethodChannel error 1 $errorMessage")
                    println("MethodChannel error 2 $errorDetails")
                }

                override fun notImplemented() {
                    println("notImplemented")
                    //表示要调用的函数在 flutter 端没有实现 ;
//                    ToastU.normal("flutter端未实现")
                }
            })
    }

}