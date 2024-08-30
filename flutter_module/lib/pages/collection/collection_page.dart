import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/bridge/flutter_bridge.dart';

class CollectionPage extends StatefulWidget {
  const CollectionPage({super.key, required this.title});

  final String title;

  @override
  State<CollectionPage> createState() => _CollectionPageState();
}

class _CollectionPageState extends State<CollectionPage> {
  @override
  void initState() {
    super.initState();
    //参考:https://huaweicloud.csdn.net/653651bb8c4ad05cd82a93b4.html
    ///注册监听，接受android端调用的update方法，并返回给android
    ///注意：在BaseApplication初始化FlutterEngine的时候，会走到这里，所以即使没有启动flutter页面，也能收到下边的回调。 测试通过。
    // FlutterBridge.getInstance().register("onRefresh", (MethodCall call) {
    //   print('------flutter端收到来自native层的消息------value=${call.arguments}');
    //   return Future.value('Flutter收到了，返回'); //flutter回信给native层
    // });
  }

  @override
  void dispose() {
    super.dispose();
    // FlutterBridge.getInstance().unRegister("onRefresh");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: GestureDetector(
          child: const Text('发送数据到android'),
          onTap: () {
            ///flutter向native发送消息，并传递参数
            // FlutterBridge.getInstance()
            //     .goToNative({"action": "goToDetail", "goodsId": "1234567"});
          },
        ),
      ),
    );
  }
}
