package com.jack.library_webview.base.callback;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 20:53
 * @描述 跟BaseWebView相关的回调
 */
public interface WebViewCallBack {
    void onPageStarted(String url);
    void onPageFinished(String url);
    void onReceivedError();
    void onReceivedHttpError();
}
