import 'package:flutter_easyloading/flutter_easyloading.dart';
import 'package:flutter_module/base/http/exception.dart';

bool handleException(ApiException exception, {bool Function(ApiException)? onError}){

  if(onError?.call(exception) == true){
    return true;
  }

  // if(exception.code == RequestConfig.tokenExpired){
  //   ///根据业务逻辑进行添加
  //   return true;
  // }

  EasyLoading.showError(exception.message ?? ApiException.unknownException);

  return false;
}