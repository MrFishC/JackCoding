import 'dart:async';

import 'package:flutter/material.dart';
import '../pages/main/main.dart';
import '../pages/second/second.dart';

///暂时先了解打开和关闭两个方法即可
class URouter extends RouterDelegate<List<RouteSettings>>
    with ChangeNotifier, PopNavigatorRouterDelegateMixin<List<RouteSettings>> {
  final List<Page> _pages = [];

  //使用场景：一般如果你的异步操作是带回调的不能即时返回结果的类型
  late Completer<Object?> _boolResultCompleter;
  static const String mainPage = "main";
  static const String secondPage = "second";

  //在构造方法中初始化_pages的数据
  URouter() {
    //预存首页
    _pages.add(_createPage(const RouteSettings(name: mainPage, arguments: [])));
    _boolResultCompleter = Completer<Object?>();
  }

  @override
  Widget build(BuildContext context) {
    //通过Navigator2.0实现
    return Navigator(
        key: navigatorKey, pages: List.of(_pages), onPopPage: _onPopPage);
  }

  @override
  GlobalKey<NavigatorState> get navigatorKey => GlobalKey<NavigatorState>();

  @override
  Future<void> setNewRoutePath(List<RouteSettings> configuration) async {}

  bool _onPopPage(Route route, dynamic result) {
    if (!route.didPop(result)) return false;

    if (_canPop()) {
      _pages.removeLast();
      return true;
    } else {
      return false;
    }
  }

  bool _canPop() {
    return _pages.length > 1;
  }

  Future<Object?> push({required String name,dynamic arguments}){
    _pages.add(_createPage(RouteSettings(name: name, arguments: arguments)));
    notifyListeners();//不增加该行 _pages是不会立马生效的   触发页面的重新绘制
    return _boolResultCompleter.future;//返回Completer的future     先记住写法，暂时不作研究
  }

  //监听页面的绘制结果 将最后一个页面删除掉，即router去重构
  @override
  Future<bool> popRoute({Object? params}) {
    if (params != null) {
      //completer.complete就相当于promise的resolve
      _boolResultCompleter.complete(params);//有参数的情况下，把参数返回回去     返回参数
    }
    if (_canPop()) {
      _pages.removeLast();
      notifyListeners();
      return Future.value(true);//表示立即完成并返回一个值为true的Future对象
    }
    return _confirmExit();
  }

  void replace({required String name, dynamic arguments}) {
    if (_pages.isNotEmpty) {
      _pages.removeLast();
    }
    push(name: name, arguments: arguments);
  }

  Future<bool> _confirmExit() async {
    print('navigatorKey = ${navigatorKey.currentContext == null}');
    final result = await showDialog<bool>(
      context: navigatorKey.currentContext!,//这里存在着bug，会为空，后续学习到了GlobalKey相关再做了解
      builder: (BuildContext context) {
        return AlertDialog(
          content: const Text('确认退出吗'),
          actions: [
            TextButton(onPressed: () => Navigator.pop(context, true), child: const Text('取消')),
            TextButton(onPressed: () => Navigator.pop(context, false), child: const Text('确定'))
          ],
        );
      },
    );
    return result ?? true;
  }

  MaterialPage _createPage(RouteSettings routeSettings){
    Widget page;
    switch(routeSettings.name){
      case mainPage:
        page = const MainPage();
        break;
      case secondPage:
        page = const SecondPage(params: "second页面");
        break;
      default:
        page = const Scaffold();
    }
    return MaterialPage(
        child: page,
        key: Key(routeSettings.name!) as LocalKey,//todo 后面再作了解 大部分场景下是不使用的，特定场景下起重要作用
        name: routeSettings.name,
        arguments: routeSettings.arguments);
  }
}
