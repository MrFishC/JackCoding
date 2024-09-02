import 'package:dio/dio.dart';
import 'package:flutter_module/model/api_response.dart';

///参考 from https://github.com/loongwind/flutter_app_core.git
///
class ApiException implements Exception {
  static const unknownException = "未知错误";
  final String? message;
  final int? code;
  String? stackInfo;

  ApiException([this.code, this.message]);

  factory ApiException.fromDioError(DioException error) {
    switch (error.type) {
      case DioExceptionType.cancel:
        return BadRequestException(-1, "请求取消");
      case DioExceptionType.connectionTimeout:
        return BadRequestException(-1, "连接超时");
      case DioExceptionType.sendTimeout:
        return BadRequestException(-1, "请求超时");
      case DioExceptionType.receiveTimeout:
        return BadRequestException(-1, "响应超时");
      case DioExceptionType.badResponse:
        try {

          /// http错误码带业务错误信息
          // ApiResponse apiResponse = ApiResponse.fromJson(error.response?.data);
          // if(apiResponse.errorCode != null){///这里应该可以省略,在下方default中包含即可
          //   return ApiException(apiResponse.errorCode, apiResponse.errorMsg);
          // }
          print("【请求data】 1 ApiException ${error.response}");
          print("【请求data】 2 ApiException ${error.response?.statusCode}");
          print("【请求data】 3 ApiException ${error.response?.data}");

          int? errCode = error.response?.statusCode;
          switch (errCode) {
            case 400:
              return BadRequestException(errCode, "请求语法错误");
            case 401:
              return UnauthorisedException(errCode!, "没有权限");
            case 403:
              return UnauthorisedException(errCode!, "服务器拒绝执行");
            case 404:
              return UnauthorisedException(errCode!, "无法连接服务器");
            case 405:
              return UnauthorisedException(errCode!, "请求方法被禁止");
            case 500:
              return UnauthorisedException(errCode!, "服务器内部错误");
            case 502:
              return UnauthorisedException(errCode!, "无效的请求");
            case 503:
              return UnauthorisedException(errCode!, "服务器异常");
            case 505:
              return UnauthorisedException(errCode!, "不支持HTTP协议请求");
            default:
              return ApiException(
                  errCode, error.response?.statusMessage ?? '未知错误');
          }
        } on Exception catch (e) {
          return ApiException(-1, unknownException);
        }
      default:
        return ApiException(-1, error.message);
    }
  }

  factory ApiException.from(dynamic exception){
    if(exception is DioException){
      return ApiException.fromDioError(exception);
    } if(exception is ApiException){
      return exception;
    } else {
      var apiException = ApiException(-1, unknownException);
      apiException.stackInfo = exception?.toString();
      return apiException;
    }
  }
}

/// 请求错误
class BadRequestException extends ApiException {
  BadRequestException([int? code, String? message]) : super(code, message);
}

/// 未认证异常
class UnauthorisedException extends ApiException {
  UnauthorisedException([int code = -1, String message = ''])
      : super(code, message);
}
