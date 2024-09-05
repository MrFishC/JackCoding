import 'package:easy_refresh/easy_refresh.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/base/model/constants.dart';
import 'package:flutter_module/base/model/events.dart';
import 'package:flutter_module/base/util/sp_util.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';
import 'package:flutter_module/controller/mine_controller.dart';
import 'package:flutter_module/util/AppColors.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';

//该页面实现我的收藏列表功能
class MinePage extends StatefulWidget {
  @override
  _MinePageState createState() => _MinePageState();
}

class _MinePageState extends State<MinePage> {
  // final _controller = Get.put(MineController());
  final _controller = Get.find<MineController>();///在MineBinding中进行了延迟初始化，这里直接获取即可

  @override
  void initState() {
    super.initState();

    // print("initState 1");//登录之后，该方法就被触发了，优化处理，进行懒加载

    ///1.获取 native 端的登录信息
    ///2.进行网络请求
    ///3.下拉刷新
    ///4.骨架屏效果
    ///
    ///
    ///

    SharedPreferencesU.preInit();
    FlutterBridge.getInstance().register(Events.postCookie, (MethodCall call) {
      print('------flutter端收到来自native层的消息------cookie=${call.arguments}');
      var cookie = call.arguments;
      // if (cookie) {//这里的if判断出现了问题，报错信息为类型转换错误。触发了MethodChannel.Result中的回调，并将报错信息传递了过去。
      if (cookie is String && cookie.isNotEmpty) {
        //保存token，执行网络请求
        // SharedPreferencesU.getInstance().setData(Constants.cookie, cookie);
        return Future.value('onSuccess'); //参数信息的字符串，可以根据需求进行自定义
      } else {
        return Future.value('onFailed');
      }
    });

    FlutterBridge.getInstance().register(Events.refresh,
        (MethodCall call) async {
      _controller.onRefresh();
    });
  }

  @override
  void dispose() {
    super.dispose();
    FlutterBridge.getInstance().unRegister(Events.postCookie);
    FlutterBridge.getInstance().unRegister(Events.refresh);
  }

  @override
  Widget build(BuildContext context) {
    ///这种方式只需在使用 flutter_screenutil 前进行初始化即可，一般放在根路由即第一个页面加载的时候进行初始化
    ///https://juejin.cn/post/7041021257562718239
    ///GetX需要先初始化
    // ScreenUtil.init(Get.context!, designSize: const Size(360, 690));
    // ScreenUtil.init(context, designSize: const Size(360, 690));//使用ScreenUtil库后，若再使用getx，则页面显示存在异常；

    return Scaffold(
      appBar: AppBar(
        // backgroundColor: Theme.of(context).canvasColor,
        title: const Text("收藏列表"),
      ),

      /// GetX改造步骤3：使用GetBuilder进行包裹
      ///参考  https://juejin.cn/post/7064483004038512648
      ///GetBuilder  是一个 Widget 组件， 在 GetX 的状态管理中，GetBuilder  的主要作用是结合 GetxController  实现界面数据的更新。当调用 GetxController 的 update 方法时，GetBuilder  包裹的 Widget 就会刷新从而实现界面数据的更新。
      body: GetBuilder<MineController>(

          ///第一次使用某个 Controller 时需要进行初始化，后续再使用同一个 Controller 就不需要再进行初始化，即不需要配置 init。
          ///初始化完成后，可以使用 Get.find() 找到对应的 Controller ：
          // init: _controller,
          builder: (controller) {
        return EasyRefresh(
          header: const PhoenixHeader(),
          footer: const PhoenixFooter(),
          onRefresh: () async {
            await Future.delayed(const Duration(seconds: 1), () {
              controller.onRefresh();
            });
          },
          onLoad: () async {
            await Future.delayed(const Duration(seconds: 1), () async {
              controller.loadMore();
            });
          },
          child: buildCustomScrollView(),
        );
      }),
    );
  }

  ///参考   --->   https://book.flutterchina.club/chapter6/custom_scrollview.html#_6-10-1-customscrollview
  CustomScrollView buildCustomScrollView() {
    // SliverFixedExtentList 是一个 Sliver，它可以生成高度相同的列表项。
    // 再次提醒，如果列表项高度相同，我们应该优先使用SliverFixedExtentList
    // 和 SliverPrototypeExtentList，如果不同，使用 SliverList.
    // var listView = SliverFixedExtentList(
    //   itemExtent: 56, //列表项高度固定
    //   delegate: SliverChildBuilderDelegate(
    //     (_, index) => ListTile(title: Text('$index')),
    //     childCount: 10,
    //   ),
    // );
    return CustomScrollView(
      slivers: [
        SliverList(
          delegate: SliverChildBuilderDelegate((context, index) {
            return buildItem(index);
          }, childCount: _controller.articleListInfo.length),
        ),
      ],
    );
  }

  Widget buildItem(int index) {
    var article = _controller.articleListInfo[index];

    print("article ------> $article");

    if (article.envelopePic.isEmpty) {
      return Container(
        margin: EdgeInsets.symmetric(vertical: 5, horizontal: 5),
        padding: EdgeInsets.symmetric(vertical: 5, horizontal: 5),
        decoration: BoxDecoration(boxShadow: [
          //卡片阴影
          BoxShadow(
            color: Colors.white,
            // offset: Offset(0, 0),
            blurRadius: 5,
          )
        ]),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Flex(
              direction: Axis.horizontal,
              children: [
                Text("新",
                    style: TextStyle(
                        fontSize: 14, color: AppColors.color_6c1bc)),
                Expanded(
                    flex: 1,
                    child: Text(article.chapterName,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(
                            fontSize: 14, color: AppColors.color_7c7b7b))),
                Text(article.niceDate,
                    style: TextStyle(
                        fontSize: 12, color: AppColors.color_999999))
              ],
            ),
            Text(article.title,
                textAlign: TextAlign.left,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style:
                TextStyle(fontSize: 14, color: AppColors.color_000000)),
            Flex(
              direction: Axis.horizontal,
              children: [
                Expanded(
                    flex: 1,
                    child: Text(article.author,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(
                            fontSize: 12, color: AppColors.color_999999))),
                Image.asset(
                  "assets/images/icon_collected.png",
                  width: 16,
                )
              ],
            )
          ],
        ),
      );
      //样式1
      // return Container(
      //   margin: EdgeInsets.symmetric(vertical: 5.h, horizontal: 5.w),
      //   padding: EdgeInsets.symmetric(vertical: 5.h, horizontal: 5.w),
      //   decoration: BoxDecoration(boxShadow: [
      //     //卡片阴影
      //     BoxShadow(
      //       color: Colors.white,
      //       // offset: Offset(0, 0),
      //       blurRadius: 5.sp,
      //     )
      //   ]),
      //   child: Column(
      //     crossAxisAlignment: CrossAxisAlignment.start,
      //     children: [
      //       Flex(
      //         direction: Axis.horizontal,
      //         children: [
      //           Text("新",
      //               style: TextStyle(
      //                   fontSize: 14.sp, color: AppColors.color_6c1bc)),
      //           Expanded(
      //               flex: 1,
      //               child: Text(article.chapterName,
      //                   maxLines: 1,
      //                   overflow: TextOverflow.ellipsis,
      //                   style: TextStyle(
      //                       fontSize: 14.sp, color: AppColors.color_7c7b7b))),
      //           Text(article.niceDate,
      //               style: TextStyle(
      //                   fontSize: 12.sp, color: AppColors.color_999999))
      //         ],
      //       ),
      //       Text(article.title,
      //           textAlign: TextAlign.left,
      //           maxLines: 2,
      //           overflow: TextOverflow.ellipsis,
      //           style:
      //               TextStyle(fontSize: 14.sp, color: AppColors.color_000000)),
      //       Flex(
      //         direction: Axis.horizontal,
      //         children: [
      //           Expanded(
      //               flex: 1,
      //               child: Text(article.author,
      //                   maxLines: 1,
      //                   overflow: TextOverflow.ellipsis,
      //                   style: TextStyle(
      //                       fontSize: 12.sp, color: AppColors.color_999999))),
      //           Image.asset(
      //             "assets/images/icon_collected.png",
      //             width: 16,
      //           )
      //         ],
      //       )
      //     ],
      //   ),
      // );
    } else {
      return Container(
        margin: EdgeInsets.symmetric(vertical: 5, horizontal: 5),
        padding: EdgeInsets.symmetric(vertical: 5, horizontal: 5),
        decoration: BoxDecoration(boxShadow: [
          //卡片阴影
          BoxShadow(
            color: Colors.white,
            // offset: Offset(0, 0),
            blurRadius: 5,
          )
        ]),
        child: Flex(
          direction: Axis.horizontal,
          children: [
            // _buildImageWidget(article.envelopePic),
            _buildImageWidget(
                "https://wbedu.sanyaedu.org/res/20240904/png/1ddad595-9094-4c74-9a20-e37ccfbb691d.png"),
            Expanded(
                flex: 1,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Flex(
                      direction: Axis.horizontal,
                      children: [
                        Text("新",
                            style: TextStyle(
                                fontSize: 14, color: AppColors.color_6c1bc)),
                        Expanded(
                            flex: 1,
                            child: Text(article.chapterName,
                                maxLines: 1,
                                overflow: TextOverflow.ellipsis,
                                style: TextStyle(
                                    fontSize: 14,
                                    color: AppColors.color_7c7b7b))),
                        Text(article.niceDate,
                            style: TextStyle(
                                fontSize: 12, color: AppColors.color_999999))
                      ],
                    ),
                    Text(article.title,
                        textAlign: TextAlign.left,
                        maxLines: 2,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(
                            fontSize: 14, color: AppColors.color_000000)),
                    Flex(
                      direction: Axis.horizontal,
                      children: [
                        Expanded(
                            flex: 1,
                            child: Text(article.author,
                                maxLines: 1,
                                overflow: TextOverflow.ellipsis,
                                style: TextStyle(
                                    fontSize: 12,
                                    color: AppColors.color_999999))),
                        Image.asset(
                          "assets/images/icon_collected.png",
                          width: 16,
                        )
                      ],
                    )
                  ],
                ))
          ],
        ),
      );
      //样式2
      // return Container(
      //   margin: EdgeInsets.symmetric(vertical: 5.h, horizontal: 5.w),
      //   padding: EdgeInsets.symmetric(vertical: 5.h, horizontal: 5.w),
      //   decoration: BoxDecoration(boxShadow: [
      //     //卡片阴影
      //     BoxShadow(
      //       color: Colors.white,
      //       // offset: Offset(0, 0),
      //       blurRadius: 5.sp,
      //     )
      //   ]),
      //   child: Flex(
      //     direction: Axis.horizontal,
      //     children: [
      //       // _buildImageWidget(article.envelopePic),
      //       _buildImageWidget(
      //           "https://wbedu.sanyaedu.org/res/20240904/png/1ddad595-9094-4c74-9a20-e37ccfbb691d.png"),
      //       Expanded(
      //           flex: 1,
      //           child: Column(
      //             crossAxisAlignment: CrossAxisAlignment.start,
      //             children: [
      //               Flex(
      //                 direction: Axis.horizontal,
      //                 children: [
      //                   Text("新",
      //                       style: TextStyle(
      //                           fontSize: 14.sp, color: AppColors.color_6c1bc)),
      //                   Expanded(
      //                       flex: 1,
      //                       child: Text(article.chapterName,
      //                           maxLines: 1,
      //                           overflow: TextOverflow.ellipsis,
      //                           style: TextStyle(
      //                               fontSize: 14.sp,
      //                               color: AppColors.color_7c7b7b))),
      //                   Text(article.niceDate,
      //                       style: TextStyle(
      //                           fontSize: 12.sp, color: AppColors.color_999999))
      //                 ],
      //               ),
      //               Text(article.title,
      //                   textAlign: TextAlign.left,
      //                   maxLines: 2,
      //                   overflow: TextOverflow.ellipsis,
      //                   style: TextStyle(
      //                       fontSize: 14.sp, color: AppColors.color_000000)),
      //               Flex(
      //                 direction: Axis.horizontal,
      //                 children: [
      //                   Expanded(
      //                       flex: 1,
      //                       child: Text(article.author,
      //                           maxLines: 1,
      //                           overflow: TextOverflow.ellipsis,
      //                           style: TextStyle(
      //                               fontSize: 12.sp,
      //                               color: AppColors.color_999999))),
      //                   Image.asset(
      //                     "assets/images/icon_collected.png",
      //                     width: 16.w,
      //                   )
      //                 ],
      //               )
      //             ],
      //           ))
      //     ],
      //   ),
      // );
    }
  }

  Widget _buildImageWidget(url) {
    return Image.network(
      url,
      height: 120.h,
      width: 75.w,
    );
  }
}
