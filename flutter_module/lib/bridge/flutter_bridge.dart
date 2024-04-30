import 'package:flutter/services.dart';

class FlutterBridge {
  static FlutterBridge _instance = FlutterBridge._();
  MethodChannel _bridge =
      const MethodChannel("FlutterBridge"); //该名称要和native端定义的名字要对应
  var _listeners = {};

  //私有的命名构造函数
  FlutterBridge._() {
    _bridge.setMethodCallHandler((MethodCall call) {
      String method = call.method;
      if (_listeners[method] != null) {
        return _listeners[method](call);
      }
      return Future.value("FlutterBridge返回");
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

  goToNative(Map params){
    _bridge.invokeMethod("goToNative",params);
  }
}
