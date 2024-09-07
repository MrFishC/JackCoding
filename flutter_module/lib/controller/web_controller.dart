import 'package:flutter_module/base/model/constants.dart';
import 'package:get/get.dart';

class WebController extends GetxController {
  String? url;

  @override
  void onInit() {
    super.onInit();
    url = Get.arguments[Constants.url];

    //  E  [ERROR:flutter/runtime/dart_vm_initializer.cc(41)] Unhandled Exception: MissingPluginException(No implementation found for method create on channel flutter/platform_views)
    // #0      MethodChannel._invokeMethod (package:flutter/src/services/platform_channel.dart:332:7)
    // <asynchronous suspension>
    // #1      SurfaceAndroidViewController._sendCreateMessage (package:flutter/src/services/platform_views.dart:1040:30)
    // <asynchronous suspension>
    // #2      AndroidViewController.create (package:flutter/src/services/platform_views.dart:827:5)
    // <asynchronous suspension>

    // update();
  }
}
