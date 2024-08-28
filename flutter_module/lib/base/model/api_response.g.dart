import 'package:flutter_module/base/model/paging_data.g.dart';
import 'package:flutter_module/generated/json/base/json_convert_content.dart';
import 'package:flutter_module/base/model/api_response.dart';

ApiResponse<T> $ApiResponseFromJson<T>(Map<String, dynamic> json) {
  final ApiResponse<T> apiResponse = ApiResponse<T>();
  final int? code = jsonConvert.convert<int>(json['code']);
  if (code != null) {
    apiResponse.errorCode = code;
  }
  final String? message = jsonConvert.convert<String>(json['message']);
  if (message != null) {
    apiResponse.errorMsg = message;
  }

  String type = T.toString();
  T? data;
  print("type:$type");
  if(json['data'] != null){
    ///特殊处理
    if(type.startsWith("PagingData<")){
      data = pagingDataFromJsonSingle<T>(json['data']);
    }else{
      data = jsonConvert.convert<T>(json['data']);
    }
  }
  if (data != null) {
    apiResponse.data = data;
  }
  return apiResponse;
}

Map<String, dynamic> $ApiResponseToJson(ApiResponse entity) {
  final Map<String, dynamic> data = <String, dynamic>{};
  data['code'] = entity.errorCode;
  data['message'] = entity.errorMsg;
  data['data'] = entity.data;
  return data;
}