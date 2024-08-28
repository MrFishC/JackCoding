import 'package:dio/dio.dart';

class TokenInterceptor extends Interceptor{

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    ///根据业务需求进行添加
    // options.headers["Authorization"] = "Basic abdc";
    // options.headers["token"] = "Bearer ";
    super.onRequest(options, handler);
  }
}