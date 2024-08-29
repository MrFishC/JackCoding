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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = KvStoreUtil.getInstance().getString(C.Login.user_token) + ""
        println("参数信息内容查看一下 token $token")
        //native发消息给flutter，传递token。Flutter端收到之后进行保存；
        FlutterBridge.instance?.native2Flutter(
            "postToken",
            token,
            object : MethodChannel.Result {
                override fun success(result: Any?) {
                    //表示调用成功 ;
                    val status = result as String
                    println("status ${status}")
                    when(status){
                        "onSuccess" ->{

                        }
                        "onFailed" ->{

                        }
                    }
//                    ToastU.normal(result as String)
                }

                override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
                    // 表示出现错误 ;
//                    ToastU.normal(errorMessage!!)
                }

                override fun notImplemented() {
                    //表示要调用的函数在 flutter 端没有实现 ;
//                    ToastU.normal("flutter端未实现")
                }
            })
    }

}