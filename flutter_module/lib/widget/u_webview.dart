import 'package:flutter/material.dart';
import 'package:flutter_module/controller/web_controller.dart';
import 'package:get/get.dart';
import 'package:webview_flutter/webview_flutter.dart';

class UWebView extends StatefulWidget {

  @override
  State<UWebView> createState() => _UWebViewState();
}

class _UWebViewState extends State<UWebView> {
  final webController = Get.find<WebController>();
  late WebViewController controller;

  @override
  void initState() {
    super.initState();
    _initWebViewController();
  }

  @override
  Widget build(BuildContext context) {
    return Text("H5");
  }

  void _initWebViewController() {
    //参考：webview_flutter使用详解
    ///https://blog.csdn.net/the_shy_faker/article/details/138174982
    controller = WebViewController()
      //是否启用JavaScript执行，默认不启用
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      //设置webview的背景色
      ..setBackgroundColor(Colors.white)
      //这里的 NavigationDecision.prevent表示阻止路由替换，NavigationDecision.navigate表示允许路由替换
      ..setNavigationDelegate(NavigationDelegate(
          onProgress: (int progress) {},
          onPageStarted: (String url) {},
          onPageFinished: (String url) {
            //页面加载完成之后才能执行JS
            _handleBackForbid();
          },
          onWebResourceError: (WebResourceError error) {},
          onNavigationRequest: (NavigationRequest request) {
            // return NavigationDecision.prevent;
            return NavigationDecision.navigate;
          }))
      //加载一个URL,uri：可以通过Uri.parse(url)来将url转成uri
      ..loadRequest(Uri.parse(webController.url!));
  }

  void _handleBackForbid() {
    // controller.runJavaScript(jsStr);
  }
}
