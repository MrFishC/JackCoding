import 'package:json_annotation/json_annotation.dart';

part 'tags.g.dart';

@JsonSerializable()
class Tags {

  // @JsonKey(name: 'name')
  String name;

  // @JsonKey(name: 'url')
  String url;

  Tags(this.name,this.url,);

  factory Tags.fromJson(Map<String, dynamic> srcJson) => _$TagsFromJson(srcJson);

}


