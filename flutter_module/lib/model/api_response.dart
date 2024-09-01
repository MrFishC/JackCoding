import 'package:json_annotation/json_annotation.dart';

// api_response.g.dart 将在我们运行生成命令后自动生成
part 'api_response.g.dart';

///参考文章：https://juejin.cn/post/7127206962915180574
/// Flutter 中解析 JSON 数据时处理泛型的实践经验
///
///生成.g.dart文件的命令： flutter packages pub run build_runner build
///这个标注是告诉生成器，这个类是需要生成Model类的
///
///genericArgumentFactories: true   表示支持泛型
@JsonSerializable(genericArgumentFactories: true)
class ApiResponse<T> {
	T? data;
	int? errorCode;
	String? errorMsg;

	ApiResponse();

	// factory ApiResponse.fromJson(Map<String, dynamic> json) => _$ApiResponseFromJson<T>(json);
	// Map<String, dynamic> toJson() => _$ApiResponseToJson(this);

  ///和正常实体类相比，fromJson 方法多了一个函数参数 T Function(dynamic json) fromJsonT；
  ///toJson 方法也多了一个函数参数：Object? Function(T value) toJsonT
	factory ApiResponse.fromJson(
			Map<String, dynamic> json,
			T Function(dynamic json) fromJsonT,
			) =>
			_$ApiResponseFromJson<T>(json, fromJsonT);

	Map<String, dynamic> toJson(Object? Function(T value) toJsonT) =>
			_$ApiResponseToJson<T>(this, toJsonT);

}