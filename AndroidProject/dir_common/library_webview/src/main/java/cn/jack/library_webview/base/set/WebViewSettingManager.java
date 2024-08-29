package cn.jack.library_webview.base.set;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.jack.library_webview.util.LogW;
import cn.jack.library_webview.util.SystemInfoUtil;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 20:56
 * @描述 webview设置类
 * <p>
 * 提供对其他各种实用设置的访问权限
 */
public class WebViewSettingManager {

    /*单例 - 静态内部类*/
    public static WebViewSettingManager getInstance() {
        return WebViewSettingManager.Holder.INSTANCE;
    }

    private static class Holder {
        private static final WebViewSettingManager INSTANCE = new WebViewSettingManager();
    }

    private WebViewSettingManager() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void settings(WebView webView) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        WebView.enableSlowWholeDocumentDraw();
        //        }

        WebSettings settings = webView.getSettings();
        //[启用 JavaScript](https://developer.android.com/guide/webapps/webview.html#EnablingJavaScript)
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);

        //        if (NetCheckHelper.getInstance().isNetworkConnected()) {
        //            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //        } else {
        //            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //        }

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //        }

        // 硬件加速兼容性 todo


        settings.setTextZoom(100);
        settings.setDatabaseEnabled(true);
//        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportMultipleWindows(false);
        //是否阻塞加载网络图片  协议http or https
        settings.setBlockNetworkImage(false);
        //允许加载本地文件html  file协议
        settings.setAllowFileAccess(true);

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        //            //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        //            mWebSettings.setAllowFileAccessFromFileURLs(false);
        //            //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        //            mWebSettings.setAllowUniversalAccessFromFileURLs(false);
        //        }

        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //        } else {
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //        }
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDefaultFontSize(16);
        //设置 WebView 支持的最小字体大小，默认为 8
        settings.setMinimumFontSize(10);
        settings.setGeolocationEnabled(true);
        settings.setUseWideViewPort(true);

        //        String appCacheDir = webView.getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        //        mWebSettings.setDatabasePath(appCacheDir);
        //        mWebSettings.setAppCachePath(appCacheDir);
        //        mWebSettings.setAppCacheMaxSize(1024*1024*80);

        // 用户可以自己设置useragent
        //        mWebSettings.setUserAgentString("webprogress/build you agent info");

        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //使用谷歌浏览器同步Android的webview页面进行调试
        //            webView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        //        }
    }
}
