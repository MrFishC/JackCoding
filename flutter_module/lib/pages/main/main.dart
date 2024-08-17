import 'package:flutter/cupertino.dart';

import '../../main.dart';
import '../../router/u_router.dart';

class MainPage extends StatelessWidget{
  const MainPage({Key? key}):super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      // child: const Text("第一个页面,点击跳转下一个页面"),
      child: const Text("关闭主页面"),
      onTap: (){
        // _openSecondActivity();
        _closeActivity();
      },
    );
  }

  Future<void> _openSecondActivity() async {
    print('开启 secondPage');
    var ack = await router.push(name: URouter.secondPage, arguments: 'Hello from mainPage');
    print('Ack: $ack');//若跳转到的目标页面被关闭了  这里会得到关闭页面携带的参数信息
  }

  Future<void> _closeActivity() async {
    var result = '关闭mainPage';
    router.popRoute(params: result);
  }
}