import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

class UWebView extends StatefulWidget {
  final String? url;
  const UWebView({
    super.key,
    this.url,
  });

  @override
  State<UWebView> createState() => _UWebViewState();
}

class _UWebViewState extends State<UWebView> {
  late WebViewController controller;
  String? url;

  @override
  void initState() {
    super.initState();
    url = widget.url;
    _initWebViewController();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Expanded(
              child: WebViewWidget(
                controller: controller,
              ))
        ],
      ),
    );
  }

  void _initWebViewController() {
    controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(const Color(0x00000000))
      ..setNavigationDelegate(NavigationDelegate(
          onProgress: (int progress) {},
          onPageStarted: (String url) {},
          onPageFinished: (String url) {
            //页面加载完成之后才能执行JS
            _handleBackForbid();
          },
          onWebResourceError: (WebResourceError error) {},
          onNavigationRequest: (NavigationRequest request) {
            // return NavigationDecision.prevent;//阻止路由替换
            return NavigationDecision.navigate;//允许进行路由替换
          }))
      ..loadRequest(Uri.parse(url!));
  }

  void _handleBackForbid() {
    // controller.runJavaScript(jsStr);
  }
}
