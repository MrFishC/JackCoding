import 'package:flutter_module/base/model/constants.dart';
import 'package:get/get.dart';

class WebController extends GetxController {
  String? url;

  @override
  void onInit() {
    super.onInit();
    url = Get.arguments[Constants.url];
  }
}
