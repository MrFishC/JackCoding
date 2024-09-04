// import 'package:dio/dio.dart';
// import 'package:flutter_module/service/request.dart';
//
// import '../pages/models/collection_mode.dart';
//
// class CollectionOfMineAPI {
//   /// 获取首页网络数据的接口方法
//   static Future<CollectionModel> homeFetch() async {
//     Response response = await XTXRequestManager().handleRequest('home/index', 'GET');
//     // 读取响应体数据中的result字段
//     dynamic ret = response.data['result'];
//
//     // 将result字段对应的首页网络数据传给首页总模型，转模型数据
//     CollectionModel collectionModel = CollectionModel.fromJson(ret);
//     return collectionModel;
//   }
// }
