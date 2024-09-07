import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

///
///Flutter内嵌H5页面的方案。在合适的场景下，该技术能够帮助我们更好地利用各类资源，构建更加高效和优秀的移动应用。
///
/// https://www.jianshu.com/p/2a794e4e3f7b
/// Flutter与Js的交互
///
/// https://juejin.cn/post/7230644271950446650
/// JavascriptChannel：通过Javascript通信。
/// MethodChannel：通过建立Binder通道和Android原生代码通信。
/// JavascriptChannel在需求短时较为直观、实现相对简单，MethodChannel在处理较复杂的逻辑时，更加清晰明了。

/// Js向Flutter传递参数
/// 1.通过URL方式
/// 2，通过JavaScriptChannels方式
///
/// Flutter 向JS传递数据
/// 1.通过URL方式
/// 2.runJavaScript
class UWebView extends StatefulWidget {
  @override
  State<UWebView> createState() => _UWebViewState();
}

class _UWebViewState extends State<UWebView> {
  // final webController = Get.find<WebController>();
  WebViewController? _controller;

  @override
  void initState() {
    super.initState();
    _initWebViewController();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Flutter Simple Example')),
      // body: WebViewWidget(controller: _controller),
      // body: Text(webController.url!),
      body: _controller != null
          ? WebViewWidget(controller: _controller!)
          : Text("webview"),
    );
  }


  void _initWebViewController() {
    //参考：webview_flutter使用详解
    ///https://blog.csdn.net/the_shy_faker/article/details/138174982
    controller = WebViewController()
      //是否启用JavaScript执行，默认不启用
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      //设置webview的背景色
      ..setBackgroundColor(const Color(0x00000000))
      //这里的 NavigationDecision.prevent表示阻止路由替换，NavigationDecision.navigate表示允许路由替换
      // ..setNavigationDelegate(NavigationDelegate(
      //     onProgress: (int progress) {},
      //     onPageStarted: (String url) {},
      //     onPageFinished: (String url) {
      //       //页面加载完成之后才能执行JS
      //       _handleBackForbid();
      //     },
      //     onWebResourceError: (WebResourceError error) {},
      //     onNavigationRequest: (NavigationRequest request) {
      //       // return NavigationDecision.prevent;//阻止路由替换
      //       return NavigationDecision.navigate;//允许进行路由替换
      //     }))
      //加载一个URL,uri：可以通过Uri.parse(url)来将url转成uri
      // ..loadRequest(Uri.parse(webController.url!));
      ..loadRequest(Uri.parse("https://juejin.cn/post/7410220431820996608"));
  }

  void _handleBackForbid() {
    // controller.runJavaScript(jsStr);
  }
}
