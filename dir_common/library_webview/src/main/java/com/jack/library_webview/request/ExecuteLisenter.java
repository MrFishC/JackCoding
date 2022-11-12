package com.jack.library_webview.request;

import android.content.Context;
import android.webkit.WebView;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 10:08
 * @描述
 */
public interface ExecuteLisenter {
    int getCommandLevel(int levelCommand);
    void executeRequest(Context context,int commandLevel, String cmd, String param,WebView webView);
    void handleCallback(String result);
}
