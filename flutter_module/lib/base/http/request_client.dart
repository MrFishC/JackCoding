import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:dio_cookie_manager/dio_cookie_manager.dart';
import 'package:flutter_module/base/http/exception.dart';
import 'package:flutter_module/base/http/http_config.dart';
import 'package:flutter_module/base/http/raw_data.dart';
import 'package:flutter_module/base/http/token_interceptor.dart';
import 'package:flutter_module/base/util/loading.dart';
import 'package:flutter_module/base/util/tool.dart';
import 'package:flutter_module/model/api_response.dart';
import 'package:pretty_dio_logger/pretty_dio_logger.dart';
import 'package:cookie_jar/cookie_jar.dart';

///全局变量
RequestClient requestClient = RequestClient();

///参考资料：[Flutter 网络请求封装之Dio](https://download.csdn.net/blog/column/9600549/90234708#Dio_3)
///
class RequestClient {
  late Dio _dio;

  RequestClient() {
    _dio = Dio(BaseOptions(
      baseUrl: RequestConfig.baseUrl,
      // //请求的Content-Type，默认值是"application/json; charset=utf-8",Headers.formUrlEncodedContentType会自动编码请求体.
      // contentType: Headers.formUrlEncodedContentType,
      // //表示期望以那种格式(方式)接受响应数据。接受4种类型 `json`, `stream`, `plain`, `bytes`. 默认值是 `json`,
      // responseType: ResponseType.json,
    ));
    final cookieJar = CookieJar();
    _dio.interceptors.add(CookieManager(cookieJar));
    _dio.interceptors.add(TokenInterceptor());
    _dio.interceptors.add(PrettyDioLogger(
        requestHeader: true, requestBody: true, responseHeader: true));
  }

  Future<T?> request<T>(String url,
      {String method = "Get",
      Map<String, dynamic>? queryParameters,
      data,
      required T Function(dynamic json) fromJsonT,
      ///增加多层泛型嵌套时的解析
      Map<String, dynamic>? headers,
      Function(ApiResponse<T>)? onResponse,
      bool Function(ApiException)? onError,
      bool? sLoading}) async {
    try {
      Options options = Options()
        ..method = method
        ..headers = headers;

      print("【请求data】1");
      data = _convertRequestData(data);
      print("【请求data】5");

      if(sLoading == true){
        showLoading();
      }
      Response response = await _dio.request(url,
          queryParameters: queryParameters, data: data, options: options);

      print("【请求data】6");
      if(sLoading == true){
        dismissLoading();
      }
      return _handleResponse<T>(response, onResponse, fromJsonT);
    } catch (e) {
      if(sLoading == true){
        dismissLoading();
      }
      var exception = ApiException.from(e);
      if (onError?.call(exception) != true) {
        throw exception;
      }
    }

    return null;
  }

  _convertRequestData(data) {
    print("【请求data】2");
    if (data != null) {
      ///将请求 data 数据先使用 jsonEncode 转换为字符串，再使用 jsonDecode 方法将字符串转换为 Map。
      print("【请求data】3");
      data = jsonDecode(jsonEncode(data));
    }
    print("【请求data】4");
    return data;
  }

  Future<T?> get<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
    required T Function(dynamic json) fromJsonT,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        queryParameters: queryParameters,
        fromJsonT: fromJsonT,
        headers: headers,
        onResponse: onResponse,
        onError: onError,
        sLoading:showLoading);
  }

  Future<T?> post<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    required T Function(dynamic json) fromJsonT,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,

  }) {
    return request(url,
        method: "POST",
        queryParameters: queryParameters,
        data: data,
        fromJsonT: fromJsonT,
        headers: headers,
        onResponse: onResponse,
        onError: onError,
        sLoading:showLoading);
  }

  Future<T?> delete<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    required T Function(dynamic json) fromJsonT,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        method: "DELETE",
        queryParameters: queryParameters,
        data: data,
        fromJsonT: fromJsonT,
        headers: headers,
        onResponse: onResponse,
        onError: onError,
        sLoading:showLoading);
  }

  Future<T?> put<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    required T Function(dynamic json) fromJsonT,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        method: "PUT",
        queryParameters: queryParameters,
        data: data,
        fromJsonT: fromJsonT,
        headers: headers,
        onResponse: onResponse,
        onError: onError,
        sLoading:showLoading);
  }

  ///请求响应内容处理
  T? _handleResponse<T>(
    Response response,
    Function(ApiResponse<T>)? onResponse,
    T Function(dynamic json) fromJsonT,
  ) {
    print("【请求data】7");
    if (response.statusCode == 200) {
      if (T.toString() == (RawData).toString()) {
        RawData raw = RawData();
        raw.value = response.data;
        return raw as T;
      } else {
        print("【请求data】8 ${response.data}");
        ApiResponse<T> apiResponse =
            ApiResponse<T>.fromJson(response.data, fromJsonT);
        print("【请求data】9");
        // ApiResponse<T> apiResponse = ApiResponse.fromJson(response.data, (json) => _fromJson<T>(json));
        onResponse?.call(apiResponse);
        print("【请求data】10");
        return _handleBusinessResponse<T>(apiResponse);
      }
    } else {
      var exception =
          ApiException(response.statusCode, ApiException.unknownException);
      throw exception;
    }
  }

  ///业务内容处理
  T? _handleBusinessResponse<T>(ApiResponse<T> response) {
    print("【请求data】 _handleBusinessResponse $response");
    if (response.errorCode == RequestConfig.successCode) {
      print("【请求data】 response.data ${response.data}");
      return response.data;
    } else if (response.errorCode == RequestConfig.tokenExpired) {
      print("【请求data】 token失效 通知native层");
      tokenInvalid();
      var exception = ApiException(response.errorCode, response.errorMsg);
      throw exception;
    } else {
      var exception = ApiException(response.errorCode, response.errorMsg);
      throw exception;
    }
  }
}
