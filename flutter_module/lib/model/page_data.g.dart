// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'page_data.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PageData<T> _$PageDataFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object? json) fromJsonT,
) =>
    PageData<T>(
      (json['curPage'] as num).toInt(),
      (json['datas'] as List<dynamic>).map(fromJsonT).toList(),
      (json['offset'] as num).toInt(),
      json['over'] as bool,
      (json['pageCount'] as num).toInt(),
      (json['size'] as num).toInt(),
      (json['total'] as num).toInt(),
    );

Map<String, dynamic> _$PageDataToJson<T>(
  PageData<T> instance,
  Object? Function(T value) toJsonT,
) =>
    <String, dynamic>{
      'curPage': instance.curPage,
      'datas': instance.datas.map(toJsonT).toList(),
      'offset': instance.offset,
      'over': instance.over,
      'pageCount': instance.pageCount,
      'size': instance.size,
      'total': instance.total,
    };
