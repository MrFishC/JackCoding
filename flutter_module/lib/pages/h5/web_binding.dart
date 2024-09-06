import 'package:flutter_module/controller/mine_controller.dart';
import 'package:flutter_module/service/api_service.dart';
import 'package:get/get.dart';

///用于懒加载对应的Controller
class MineBinding extends Bindings {
  @override
  void dependencies() {
    ///延迟初始化，需要用的时候才进行初始化
    Get.lazyPut(() => MineController());
    Get.lazyPut(() => ApiService());
  }
}