import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/base/model/constants.dart';
import 'package:flutter_module/base/model/events.dart';
import 'package:flutter_module/base/util/sp_util.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';

class MinePage extends StatefulWidget {
  @override
  _MinePageState createState() => _MinePageState();
}

class _MinePageState extends State<MinePage> {
  @override
  void initState() {
    super.initState();

    // print("initState 1");//登录之后，该方法就被触发了，优化处理，进行懒加载

    ///1.获取 native 端的登录信息
    ///2.进行网络请求
    ///3.下拉刷新
    ///4.骨架屏效果
    ///
    ///
    ///

    SharedPreferencesU.preInit();
    FlutterBridge.getInstance().register(Events.postCookie, (MethodCall call) {
      print('------flutter端收到来自native层的消息------cookie=${call.arguments}');
      var cookie = call.arguments;
      // if (cookie) {//这里的if判断出现了问题，报错信息为类型转换错误。触发了MethodChannel.Result中的回调，并将报错信息传递了过去。
      if (cookie is String && cookie.isNotEmpty) {
        //保存token，执行网络请求
        SharedPreferencesU.getInstance().setData(Constants.cookie, cookie);
        return Future.value('onSuccess'); //参数信息的字符串，可以根据需求进行自定义
      } else {
        return Future.value('onFailed');
      }
    });

    FlutterBridge.getInstance().register(Events.refresh, (MethodCall call) {
      var cookieInfo = SharedPreferencesU.getInstance().get(Constants.cookie);
      print("刷新列表数据 cookieInfo = $cookieInfo");
    });

    // print("initState 2")
    //
    // var cookieInfo = SharedPreferencesU.getInstance().get(Constants.cookie)
    // print("initState 3 cookieInfo = $cookieInfo")
  }

  @override
  void dispose() {
    super.dispose();
    FlutterBridge.getInstance().unRegister(Events.postCookie);
    FlutterBridge.getInstance().unRegister(Events.refresh);
  }

  @override
  Widget build(BuildContext context) {
    ///这种方式只需在使用 flutter_screenutil 前进行初始化即可，一般放在根路由即第一个页面加载的时候进行初始化
    // ScreenUtil.init(Get.context!, designSize: const Size(360, 690)); //存在报错

    return Scaffold(
      body: Container(),
    );
  }
}
