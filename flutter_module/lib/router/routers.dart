import 'package:flutter_module/pages/mine/mine_binding.dart';
import 'package:flutter_module/pages/mine/mine_page.dart';
import 'package:flutter_module/widget/u_webview.dart';
import 'package:get/get.dart';

class RouteGet {
  ///root page
  static const String mine = "/";
  static const String web = "/web";

  ///getx的使用
  ///https://github.com/jonataslaw/getx/blob/master/documentation/zh_CN/route_management.md
  ///GetX 的集成和主要功能：状态管理、依赖管理、路由管理的使用都已经实现了，可以开始项目开发了
  ///
  ///pages map
  static final List<GetPage> getPages = [
    //定义别名  页面    映射关系
    GetPage(name: mine, page: () => MinePage(), binding: MineBinding()),///这里使用的 Bindings  的方式，会自动在 Binding 里注册 Controller ， 并且在 Page 里生成 find Controller 的代码。
    GetPage(
        name: web,
        page: () => UWebView()),
        // page: () => UWebView(),binding: WebBinding()),
  ];
}