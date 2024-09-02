import 'package:json_annotation/json_annotation.dart';

part 'test_bean_info.g.dart';

///测试数据解析使用
@JsonSerializable(genericArgumentFactories: true)
class TestBeanInfo<T>{

  @JsonKey(name: 'adminAdd')
  bool? adminAdd;

  @JsonKey(name: 'apkLink')
  String? apkLink;

  @JsonKey(name: 'audit')
  int? audit;

  @JsonKey(name: 'author')
  String? author;

  @JsonKey(name: 'canEdit')
  bool? canEdit;

  @JsonKey(name: 'chapterId')
  int? chapterId;

  @JsonKey(name: 'chapterName')
  String? chapterName;

  @JsonKey(name: 'collect')
  bool? collect;

  @JsonKey(name: 'courseId')
  int? courseId;

  @JsonKey(name: 'desc')
  String? desc;

  @JsonKey(name: 'descMd')
  String? descMd;

  @JsonKey(name: 'envelopePic')
  String? envelopePic;

  @JsonKey(name: 'fresh')
  bool? fresh;

  @JsonKey(name: 'host')
  String? host;

  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'isAdminAdd')
  bool? isAdminAdd;

  @JsonKey(name: 'link')
  String? link;

  @JsonKey(name: 'niceDate')
  String? niceDate;

  @JsonKey(name: 'niceShareDate')
  String? niceShareDate;

  @JsonKey(name: 'origin')
  String? origin;

  @JsonKey(name: 'prefix')
  String? prefix;

  @JsonKey(name: 'projectLink')
  String? projectLink;

  @JsonKey(name: 'publishTime')
  int? publishTime;

  @JsonKey(name: 'realSuperChapterId')
  int? realSuperChapterId;

  @JsonKey(name: 'selfVisible')
  int? selfVisible;

  @JsonKey(name: 'shareDate')
  int? shareDate;

  @JsonKey(name: 'shareUser')
  String? shareUser;

  @JsonKey(name: 'superChapterId')
  int? superChapterId;

  @JsonKey(name: 'superChapterName')
  String? superChapterName;

  @JsonKey(name: 'title')
  String? title;

  @JsonKey(name: 'type')
  int? type;

  @JsonKey(name: 'userId')
  int? userId;

  @JsonKey(name: 'visible')
  int? visible;

  @JsonKey(name: 'zan')
  int? zan;

  T? data;

  TestBeanInfo();

  factory TestBeanInfo.fromJson(
      Map<String, dynamic> json,
      T Function(dynamic json) fromJsonT,
      ) =>
      _$TestBeanInfoFromJson<T>(json, fromJsonT);

  Map<String, dynamic> toJson(Object? Function(T value) toJsonT) =>
      _$TestBeanInfoToJson<T>(this, toJsonT);
}


