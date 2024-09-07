import 'package:flutter_module/controller/web_controller.dart';
import 'package:get/get.dart';

class WebBinding extends Bindings {
  @override
  void dependencies() {
    ///延迟初始化，需要用的时候才进行初始化
    Get.lazyPut(() => WebController());
  }
}