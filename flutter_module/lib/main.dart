import 'package:flutter/material.dart';
import 'package:flutter_module/pages/collection/collection_page.dart';
import 'package:flutter_module/pages/mall/mall_home_page.dart';

void main() => runApp(const MyApp(CollectionPage(
      title: "flutter混合开发",
    )));

//必须加注解,注册成入口，collection同FlutterCacheManager中定义的一致
@pragma('vm:entry-point')
void collection() => runApp(const MyApp(CollectionPage(
      title: "收藏",
    )));

@pragma('vm:entry-point')
void mall() => runApp(MyApp(MallHomePage()));

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
