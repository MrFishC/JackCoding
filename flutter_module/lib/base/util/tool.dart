import 'package:flutter_module/bridge/flutter_bridge.dart';

void tokenInvalid(){
  FlutterBridge.getInstance().goToNative({"action": "goToLogin"});
}