import 'package:flutter/services.dart';

class FlutterBridge {
  static final FlutterBridge _instance = FlutterBridge._();
  final MethodChannel _bridge =
      const MethodChannel("FlutterBridge"); //该名称要和native端定义的名字要对应
  final _listeners = {};

  //私有的命名构造函数
  FlutterBridge._() {
    _bridge.setMethodCallHandler((MethodCall call) {
      String method = call.method;
      // print("FlutterBridge 执行 1 $method");//
      if (_listeners[method] != null) {
        // print("FlutterBridge 执行 2 $call");
        return _listeners[method](call);
      }
      // print("FlutterBridge 执行 3");
      return Future.value("onCompleted");
    });
  }

  static FlutterBridge getInstance() {
    return _instance;
  }

  register(String method, Function(MethodCall) callBack) {
    _listeners[method] = callBack;
  }

  unRegister(String method) {
    _listeners.remove(method);
  }

  MethodChannel bridge() {
    return _bridge;
  }

  goToNative(Map params) {
    _bridge.invokeMethod("goToNative", params);
  }
}
