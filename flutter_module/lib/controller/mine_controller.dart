import 'package:flutter_module/model/article.dart';
import 'package:flutter_module/model/article_tags.dart';
import 'package:flutter_module/model/page_data.dart';
import 'package:flutter_module/service/api_service.dart';
import 'package:get/get.dart';

/// GetX改造步骤2：创建controller，继承自GetxController
class MineController extends GetxController {
  ///getX改造
  // final ApiService apiService = Get.put(ApiService());
  final ApiService apiService = Get.find<ApiService>();

  List<Article<ArticleTags>> articleListInfo = [];
  int page = 0;

  void onRefresh() async {
    page = 0;
    await Future.delayed(const Duration(seconds: 2));
    PageData<Article<ArticleTags>>? result = await apiService.getArticleList(page);
    articleListInfo = result!.datas;
    update();
  }

  void loadMore() {
    page++;
    apiService.getArticleList(page).then((result) {
      articleListInfo.addAll(result!.datas);
      update();
    });
  }
}
