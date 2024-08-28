import 'dart:convert';
import 'package:dio/dio.dart';
import 'package:flutter_module/base/http/exception.dart';
import 'package:flutter_module/base/http/http_config.dart';
import 'package:flutter_module/base/http/raw_data.dart';
import 'package:flutter_module/base/http/token_interceptor.dart';
import 'package:flutter_module/base/model/api_response.dart';
import 'package:pretty_dio_logger/pretty_dio_logger.dart';

///全局变量
RequestClient requestClient = RequestClient();

class RequestClient {
  late Dio _dio;

  RequestClient() {
    _dio = Dio(BaseOptions(baseUrl: RequestConfig.baseUrl));
    _dio.interceptors.add(TokenInterceptor());
    _dio.interceptors.add(PrettyDioLogger(
        requestHeader: true, requestBody: true, responseHeader: true));
  }

  Future<T?> request<T>(
    String url, {
    String method = "Get",
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) async {
    try {
      Options options = Options()
        ..method = method
        ..headers = headers;

      data = _convertRequestData(data);

      Response response = await _dio.request(url,
          queryParameters: queryParameters, data: data, options: options);

      return _handleResponse<T>(response, onResponse);
    } catch (e) {
      var exception = ApiException.from(e);
      if (onError?.call(exception) != true) {
        throw exception;
      }
    }

    return null;
  }

  _convertRequestData(data) {
    if (data != null) {
      ///将请求 data 数据先使用 jsonEncode 转换为字符串，再使用 jsonDecode 方法将字符串转换为 Map。
      data = jsonDecode(jsonEncode(data));
    }
    return data;
  }

  Future<T?> get<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        queryParameters: queryParameters,
        headers: headers,
        onResponse: onResponse,
        onError: onError);
  }

  Future<T?> post<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        method: "POST",
        queryParameters: queryParameters,
        data: data,
        headers: headers,
        onResponse: onResponse,
        onError: onError);
  }

  Future<T?> delete<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        method: "DELETE",
        queryParameters: queryParameters,
        data: data,
        headers: headers,
        onResponse: onResponse,
        onError: onError);
  }

  Future<T?> put<T>(
    String url, {
    Map<String, dynamic>? queryParameters,
    data,
    Map<String, dynamic>? headers,
    bool showLoading = true,
    Function(ApiResponse<T>)? onResponse,
    bool Function(ApiException)? onError,
  }) {
    return request(url,
        method: "PUT",
        queryParameters: queryParameters,
        data: data,
        headers: headers,
        onResponse: onResponse,
        onError: onError);
  }

  ///请求响应内容处理
  T? _handleResponse<T>(
    Response response,
    Function(ApiResponse<T>)? onResponse,
  ) {
    if (response.statusCode == 200) {
      if (T.toString() == (RawData).toString()) {
        RawData raw = RawData();
        raw.value = response.data;
        return raw as T;
      } else {
        ApiResponse<T> apiResponse = ApiResponse<T>.fromJson(response.data);
        onResponse?.call(apiResponse);
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
    if (response.errorCode == RequestConfig.successCode) {
      return response.data;
    } else {
      var exception = ApiException(response.errorCode, response.errorMsg);
      throw exception;
    }
  }
}
