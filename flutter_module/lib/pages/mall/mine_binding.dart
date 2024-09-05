import 'package:flutter_module/controller/mine_controller.dart';
import 'package:flutter_module/service/api_service.dart';
import 'package:get/get.dart';

///用于懒加载对应的Controller
///参考：https://juejin.cn/post/7039637076962181157
class MineBinding extends Bindings {
  @override
  void dependencies() {
    ///延迟初始化，在需要用到的时候才会初始化实例对象，即第一次 find 某一个类的时候才会进行初始化。
    Get.lazyPut(() => MineController());
    Get.lazyPut(() => ApiService());
  }
}