import 'package:json_annotation/json_annotation.dart';

part 'page_data.g.dart';

@JsonSerializable(genericArgumentFactories: true)
class PageData<T>{

  // @JsonKey(name: 'curPage')
  int curPage;

  // @JsonKey(name: 'datas')
  List<T> datas;

  // @JsonKey(name: 'offset')
  int offset;

  // @JsonKey(name: 'over')
  bool over;

  // @JsonKey(name: 'pageCount')
  int pageCount;

  // @JsonKey(name: 'size')
  int size;

  // @JsonKey(name: 'total')
  int total;

  PageData(this.curPage,this.datas,this.offset,this.over,this.pageCount,this.size,this.total,);

  factory PageData.fromJson(Map<String, dynamic> srcJson,T Function(dynamic json) fromJsonT) => _$PageDataFromJson<T>(srcJson,fromJsonT);

  Map<String, dynamic> toJson(Object? Function(T value) toJsonT) => _$PageDataToJson<T>(this, toJsonT);

}



