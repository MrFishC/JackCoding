import 'package:flutter/material.dart';
import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:flutter_module/router/routers.dart';
import 'package:flutter_module/router/u_router.dart';
import 'package:get/get.dart';

import 'pages/mall/mall_home_page.dart';

void main() => runApp(const MyApp());

@pragma('vm:entry-point')
void mine() => runApp(const MyApp());

//必须加注解,注册成入口，collection同FlutterCacheManager中定义的一致
// @pragma('vm:entry-point')
// void collection() => runApp(const MyApp(CollectionPage(
//       title: "收藏",
//     )));

@pragma('vm:entry-point')
void mall() {
  ///https://github.com/OpenFlutter/flutter_screenutil/blob/master/README_CN.md
  runApp(const MyApp2());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    ///https://juejin.cn/post/7039637076962181157
    ///初始化getx
    /// GetX改造步骤：1、修改MaterialApp成GetMaterialApp
    return GetMaterialApp(
      initialRoute: RouteGet.mine,//初始页面
      // initialRoute: RouteGet.main,//初始页面
      getPages: RouteGet.getPages,//路由映射集合
      // initialBinding: MineBinding(),//注释，改用getx的别名路由实现
      // home: MinePage(),//注释，改用getx的别名路由实现
      builder: EasyLoading.init(),
    );
  }
}

URouter router = URouter();

class MyApp2 extends StatelessWidget {
  const MyApp2({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '欢迎学习Flutter模块',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      // home: Router(
      //   routerDelegate: router,
      //   //所有的路由都有这个代理来完成   首页是按照router中的_pages来决定的，排在集合第一个的为首页。因此_pages中需要有数据
      //   backButtonDispatcher:
      //       RootBackButtonDispatcher(), //返回的时候进行处理，暂时不需要过多的去了解
      // ),
      home: MallHomePage(),
    );
  }
}
