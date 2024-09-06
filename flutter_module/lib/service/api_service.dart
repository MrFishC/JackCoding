import 'package:flutter_module/base/http/request_client.dart';
import 'package:flutter_module/model/article.dart';
import 'package:flutter_module/model/article_tags.dart';
import 'package:flutter_module/model/page_data.dart';
import 'package:get/get.dart';

class ApiService extends GetxService {
  Future<PageData<Article<ArticleTags>>?> getArticleList(int page) {
    return requestClient.get<PageData<Article<ArticleTags>>>(
        "/article/list/$page/json",
        // "/lg/collect/list/$page/json",//一直提示登录失效
        fromJsonT: (json) => PageData.fromJson(
            json,
            (json) =>
                Article.fromJson(json, (json) => ArticleTags.fromJson(json))));
  }
}
