// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'test_bean_info.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TestBeanInfo<T> _$TestBeanInfoFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object? json) fromJsonT,
) =>
    TestBeanInfo<T>()
      ..adminAdd = json['adminAdd'] as bool?
      ..apkLink = json['apkLink'] as String?
      ..audit = (json['audit'] as num?)?.toInt()
      ..author = json['author'] as String?
      ..canEdit = json['canEdit'] as bool?
      ..chapterId = (json['chapterId'] as num?)?.toInt()
      ..chapterName = json['chapterName'] as String?
      ..collect = json['collect'] as bool?
      ..courseId = (json['courseId'] as num?)?.toInt()
      ..desc = json['desc'] as String?
      ..descMd = json['descMd'] as String?
      ..envelopePic = json['envelopePic'] as String?
      ..fresh = json['fresh'] as bool?
      ..host = json['host'] as String?
      ..id = (json['id'] as num?)?.toInt()
      ..isAdminAdd = json['isAdminAdd'] as bool?
      ..link = json['link'] as String?
      ..niceDate = json['niceDate'] as String?
      ..niceShareDate = json['niceShareDate'] as String?
      ..origin = json['origin'] as String?
      ..prefix = json['prefix'] as String?
      ..projectLink = json['projectLink'] as String?
      ..publishTime = (json['publishTime'] as num?)?.toInt()
      ..realSuperChapterId = (json['realSuperChapterId'] as num?)?.toInt()
      ..selfVisible = (json['selfVisible'] as num?)?.toInt()
      ..shareDate = (json['shareDate'] as num?)?.toInt()
      ..shareUser = json['shareUser'] as String?
      ..superChapterId = (json['superChapterId'] as num?)?.toInt()
      ..superChapterName = json['superChapterName'] as String?
      ..title = json['title'] as String?
      ..type = (json['type'] as num?)?.toInt()
      ..userId = (json['userId'] as num?)?.toInt()
      ..visible = (json['visible'] as num?)?.toInt()
      ..zan = (json['zan'] as num?)?.toInt()
      ..data = _$nullableGenericFromJson(json['data'], fromJsonT);

Map<String, dynamic> _$TestBeanInfoToJson<T>(
  TestBeanInfo<T> instance,
  Object? Function(T value) toJsonT,
) =>
    <String, dynamic>{
      'adminAdd': instance.adminAdd,
      'apkLink': instance.apkLink,
      'audit': instance.audit,
      'author': instance.author,
      'canEdit': instance.canEdit,
      'chapterId': instance.chapterId,
      'chapterName': instance.chapterName,
      'collect': instance.collect,
      'courseId': instance.courseId,
      'desc': instance.desc,
      'descMd': instance.descMd,
      'envelopePic': instance.envelopePic,
      'fresh': instance.fresh,
      'host': instance.host,
      'id': instance.id,
      'isAdminAdd': instance.isAdminAdd,
      'link': instance.link,
      'niceDate': instance.niceDate,
      'niceShareDate': instance.niceShareDate,
      'origin': instance.origin,
      'prefix': instance.prefix,
      'projectLink': instance.projectLink,
      'publishTime': instance.publishTime,
      'realSuperChapterId': instance.realSuperChapterId,
      'selfVisible': instance.selfVisible,
      'shareDate': instance.shareDate,
      'shareUser': instance.shareUser,
      'superChapterId': instance.superChapterId,
      'superChapterName': instance.superChapterName,
      'title': instance.title,
      'type': instance.type,
      'userId': instance.userId,
      'visible': instance.visible,
      'zan': instance.zan,
      'data': _$nullableGenericToJson(instance.data, toJsonT),
    };

T? _$nullableGenericFromJson<T>(
  Object? input,
  T Function(Object? json) fromJson,
) =>
    input == null ? null : fromJson(input);

Object? _$nullableGenericToJson<T>(
  T? input,
  Object? Function(T value) toJson,
) =>
    input == null ? null : toJson(input);
