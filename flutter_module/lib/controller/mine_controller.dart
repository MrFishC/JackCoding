import 'package:flutter_module/model/article.dart';
import 'package:flutter_module/model/article_tags.dart';
import 'package:flutter_module/service/api_service.dart';
import 'package:get/get.dart';

/// GetX改造步骤2：创建controller，继承自GetxController
class MineController extends GetxController {
  ///getX改造
  final ApiService apiService = Get.find();

  List<Article<ArticleTags>> articleListInfo = [];
  int page = 0;

  // Future<PageData<Article<ArticleTags>>?> get loadData =>
  //     requestClient.get<PageData<Article<ArticleTags>>>(
  //         "/article/list/$page/json",
  //         fromJsonT: (json) => PageData.fromJson(
  //             json,
  //             (json) => Article.fromJson(
  //                 json, (json) => ArticleTags.fromJson(json))));

  void onRefresh() {
    page = 0;
    apiService.getArticleList(page).then((result) {
      articleListInfo = result!.datas;
      print("================================ 1 ");
      print(articleListInfo.length);
      print("================================ 2 ");
      update();
    });
  }

  void loadMore() {
    page++;
    apiService.getArticleList(page).then((result) {
      print("================================ 3 ");
      articleListInfo.addAll(result!.datas);
      print("================================ 4 ");
      update();
    });
  }
}
