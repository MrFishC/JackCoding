import 'package:json_annotation/json_annotation.dart';

part 'article_tags.g.dart';

@JsonSerializable()
class ArticleTags {

  // @JsonKey(name: 'name')
  String name;

  // @JsonKey(name: 'url')
  String url;

  ArticleTags(this.name,this.url,);

  factory ArticleTags.fromJson(Map<String, dynamic> srcJson) => _$TagsFromJson(srcJson);

  Map<String, dynamic> toJson() =>
      _$TagsToJson(this);
}


