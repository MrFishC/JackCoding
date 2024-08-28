import 'package:flutter/material.dart';
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
    ///1.获取native端的登录信息
    ///2.进行网络请求
  }

  @override
  Widget build(BuildContext context) {
    ///这种方式只需在使用 flutter_screenutil 前进行初始化即可，一般放在根路由即第一个页面加载的时候进行初始化
    ScreenUtil.init(Get.context!, designSize: const Size(360, 690));

    return Scaffold(
      body: Container(),
    );
  }
}
