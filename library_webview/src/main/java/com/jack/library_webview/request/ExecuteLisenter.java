package com.jack.library_webview.request;

import android.content.Context;
import android.webkit.WebView;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:08
 * @描述
 */
public interface ExecuteLisenter {
    int getCommandLevel();      //这个标记可以去掉，通过H5页面传递过来的类型进行动态判断
    void executeRequest(Context context, int commandLevel, String cmd, WebView webView);
    void handleCallback(String result);
}
