// This is a basic Flutter widget test.
//
// To perform an interaction with a widget in your test, use the WidgetTester
// utility in the flutter_test package. For example, you can send tap and scroll
// gestures. You can also use WidgetTester to find child widgets in the widget
// tree, read text, and verify that the values of widget properties are correct.

import 'dart:convert';
import 'package:flutter_module/model/api_response.dart';
import 'package:flutter_module/model/article.dart';
import 'package:flutter_module/model/article_tags.dart';
import 'package:flutter_module/model/page_data.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  testWidgets('Counter increments smoke test', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    // await tester.pumpWidget(const MyApp(CollectionPage(
    //   title: "flutter混合开发",
    // )));
    //
    // // Verify that our counter starts at 0.
    // expect(find.text('0'), findsOneWidget);
    // expect(find.text('1'), findsNothing);
    //
    // // Tap the '+' icon and trigger a frame.
    // await tester.tap(find.byIcon(Icons.add));
    // await tester.pump();
    //
    // // Verify that our counter has incremented.
    // expect(find.text('0'), findsNothing);
    // expect(find.text('1'), findsOneWidget);

//     String str = """
//      {"data":{"curPage":1,"datas":[
// {"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":28871,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/8c9QlsXexr9XufcKEd2yOA","niceDate":"2024-08-29 00:00","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1724860800000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1724980651000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android 现代应用架构更佳实践","type":0,"userId":-1,"visible":1,"zan":0}
// ],"offset":0,"over":false,"pageCount":783,"size":20,"total":15643},"errorCode":0,"errorMsg":""}
//      """;
//
//     Map<String, dynamic> json = jsonDecode(str);
//     ApiResponse<PageData<Article<ArticleTags>>> result =
//         ApiResponse.fromJson(json, (json) {
//       return PageData.fromJson(
//           json,
//           (json) =>
//               Article.fromJson(json, (json) => ArticleTags.fromJson(json)));
//     });
//     print("解析数据1 ${result}");
//     print("解析数据2 ${result.data}");
//     print("解析数据3 ${result.errorCode}");
//     print("解析数据4 ${result.errorMsg}");
//     print("解析数据5 ${result.data?.datas.length}");
//     print("解析数据5 ${result.data?.datas}");
//     print("解析数据6 ${result.data?.datas[0].tags[0].toJson()}");

    String str2 = """
     {"data":123,"errorCode":0,"errorMsg":""}
     """;
    Map<String, dynamic> json2 = jsonDecode(str2);
    ApiResponse<int> result2 = ApiResponse.fromJson(json2,(json) => json as int);
    print("解析数据7 ${result2.data}");
  });
}
