import 'package:flutter/material.dart';
import 'package:flutter_module/pages/collection/collection_page.dart';
import 'package:flutter_module/pages/mine/mine_page.dart';
import 'package:flutter_module/router/u_router.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

void main() =>
    runApp(const MyApp(CollectionPage(
      title: "flutter混合开发",
    )));

@pragma('vm:entry-point')
void mine() => runApp(MyApp(MinePage()));

//必须加注解,注册成入口，collection同FlutterCacheManager中定义的一致
@pragma('vm:entry-point')
void collection() =>
    runApp(const MyApp(CollectionPage(
      title: "收藏",
    )));

@pragma('vm:entry-point')
void mall() {
  ///https://github.com/OpenFlutter/flutter_screenutil/blob/master/README_CN.md
  runApp(const MyApp2());
}

class MyApp extends StatelessWidget {
  final Widget page;

  const MyApp(this.page, {super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Scaffold(
        body: page,
      ),
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
      home: Router(
        routerDelegate: router,
        //所有的路由都有这个代理来完成   首页是按照router中的_pages来决定的，排在集合第一个的为首页。因此_pages中需要有数据
        backButtonDispatcher: RootBackButtonDispatcher(), //返回的时候进行处理，暂时不需要过多的去了解
      ),
    );
  }
}
