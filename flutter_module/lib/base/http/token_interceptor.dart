import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_module/base/model/constants.dart';
import 'package:flutter_module/base/util/sp_util.dart';

class TokenInterceptor extends Interceptor {
  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    ///根据业务需求进行添加
    // var cookieInfo = SharedPreferencesU.getInstance().get(Constants.cookie);
    //   print("【请求data】 cookieInfo $cookieInfo");
    // options.headers["set-cookie"] = cookieInfo;///使用了CookieManager，这里就注释
    if (kDebugMode) {
      print("【请求data】 onRequest ${options.headers["set-cookie"]}");
      print("【请求data】 onRequest ${options.headers["cookie"]}");
    }
    super.onRequest(options, handler);
  }

  @override
  void onResponse(Response response, ResponseInterceptorHandler handler) {
    // SharedPreferencesU.getInstance().setData(Constants.cookie, response.headers["set-cookie"]);
    if (kDebugMode) {
      print("【返回data】onResponse ${response.headers}");
      print("【返回data】${response.data.toString()}");
    }
    super.onResponse(response, handler);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    if (kDebugMode) {
      print("========================请求错误===================");
    }
    super.onError(err, handler);
  }
}
