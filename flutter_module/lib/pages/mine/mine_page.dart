import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/base/model/constants.dart';
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
    ///1.获取 native 端的登录信息
    ///2.进行网络请求
    ///3.下拉刷新
    ///4.骨架屏效果
    ///
    ///
    SharedPreferencesU.preInit();
    FlutterBridge.getInstance().register("postToken", (MethodCall call) {
      print('------flutter端收到来自native层的消息------token=${call.arguments}');
      var token = call.arguments;
      if(token){
        //保存token，执行网络请求
        SharedPreferencesU.getInstance().setData(Constants.token, token);
      }else{
        return Future.value('onFailed');
      }
      // return Future.value('Flutter收到了，返回'); //flutter回信给native层
    });
  }

  @override
  void dispose() {
    super.dispose();
    FlutterBridge.getInstance().unRegister("postToken");
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
