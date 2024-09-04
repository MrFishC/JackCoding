import 'package:flutter_module/base/http/request_client.dart';
import 'package:flutter_module/model/article.dart';
import 'package:flutter_module/model/article_tags.dart';
import 'package:flutter_module/model/page_data.dart';

class MineController {
  List<Article<ArticleTags>> articleListInfo = [];
  int page = 0;

  Future<PageData<Article<ArticleTags>>?> get loadData =>
      requestClient.get<PageData<Article<ArticleTags>>>(
          "/article/list/$page/json",
          fromJsonT: (json) => PageData.fromJson(
              json,
              (json) => Article.fromJson(
                  json, (json) => ArticleTags.fromJson(json))));
}
