//package cn.jack.module_fragment_05
//
//import android.os.Bundle
//import android.view.View
//import cn.jack.lib_common.flutter.base.BaseFlutterFragment
//import cn.jack.module_hybird.MultipleModulesNameProvider
//import cn.jack.lib_common.flutter.bridge.FlutterBridge
//import cn.jack.library_arouter.manager.constants.RouterPathFragment
//import cn.jack.library_util.ToastU
//import com.alibaba.android.arouter.facade.annotation.Route
//import io.flutter.plugin.common.MethodChannel
//
///**
// * 使用flutter实现
// * 备份
// */
//@Route(path = RouterPathFragment.HomeFive.PAGER_HOME_FIVE)
//class ModuleFragment06 : BaseFlutterFragment(MultipleModulesNameProvider.MODULE_NAME_COLLECTION) {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //native发消息给flutter
//        FlutterBridge.instance?.native2Flutter(
//            "onRefresh",
//            "测试的参数信息",
//            object : MethodChannel.Result {
//                override fun success(result: Any?) {
//                    //表示调用成功 ;
//                    ToastU.normal(result as String)
//                }
//
//                override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
//                    // 表示出现错误 ;
//                    ToastU.normal(errorMessage!!)
//                }
//
//                override fun notImplemented() {
//                    //表示要调用的函数在 flutter 端没有实现 ;
//                    ToastU.normal("flutter端未实现")
//                }
//            })
//    }
//}