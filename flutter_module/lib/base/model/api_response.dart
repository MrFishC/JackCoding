// import 'dart:convert';
// import 'package:flutter_module/base/model/api_response.g.dart';
//
// // @JsonSerializable()
// class ApiResponse<T> {
// 	T? data;
// 	int? errorCode;
// 	String? errorMsg;
//
// 	ApiResponse();
//
// 	factory ApiResponse.fromJson(Map<String, dynamic> json) => $ApiResponseFromJson<T>(json);
//
// 	Map<String, dynamic> toJson() => $ApiResponseToJson(this);
//
// 	@override
// 	String toString() {
// 		return jsonEncode(this);
// 	}
// }
//
// // @JsonSerializable()
// // class ApiResponseData {
// // 	ApiResponseData();
// //
// // 	factory ApiResponseData.fromJson(Map<String, dynamic> json) => $ApiResponseDataFromJson(json);
// //
// // 	Map<String, dynamic> toJson() => $ApiResponseDataToJson(this);
// //
// // 	@override
// // 	String toString() {
// // 		return jsonEncode(this);
// // 	}
// // }