import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

class MallHomePage extends StatefulWidget {
  @override
  _MallHomePageState createState() => _MallHomePageState();
}

class _MallHomePageState extends State<MallHomePage> {
  late WebViewController controller;

  @override
  void initState() {
    super.initState();
    _initWebViewController();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Flutter Webview')),
      body: WebViewWidget(controller: controller),
    );
  }

  void _initWebViewController() {
    controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setBackgroundColor(const Color(0x00000000))
      ..setNavigationDelegate(
        NavigationDelegate(
          onProgress: (int progress) {
            // Update loading bar.
          },
          onPageStarted: (String url) {},
          onPageFinished: (String url) {
            //页面加载完成后才能执行js
            _handleBackForbid();
          },
          onWebResourceError: (WebResourceError error) {},
          onNavigationRequest: (NavigationRequest request) {
            return NavigationDecision.navigate;
          },
        ),
      )
      ..loadRequest(Uri.parse('https://www.geekailab.com'));
  }

  void _handleBackForbid() {
    // controller.runJavaScript(jsStr);
  }
}
