package cn.jack.library_webview.base.wbclient;

import android.webkit.WebChromeClient;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 14:54
 * @描述
 *
 * H5页面弹对话框，一共有三种方式
 *
 * 1.webview调用setWebChromeClient方法设置WebChromeClient
 * 2.调用setWebChromeClient方法，重写onJsAlert，可自定义原生的dialog。不过这里需要注意dialog挟持的问题；
 * 3.通过js桥，让h5调用原生的方法。注意：这里需要区分是否在同一个进程。
 */
public class CostomWebChromeClient extends WebChromeClient {

//    @Override
//    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//        return super.onJsAlert(view, url, message, result);
//    }

}
