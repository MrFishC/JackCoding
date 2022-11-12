package com.jack.library_webview.base.wbclient;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.jack.library_webview.base.callback.WebViewCallBack;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 20:58
 * @描述 自定义实现WebViewClient，拓展功能和解耦
 *
 * [处理网页导航](https://developer.android.com/guide/webapps/webview.html#HandlingNavigation)
 *
 */
public class CostomWebViewClient extends WebViewClient {

    public static final String CONTENT_SCHEME = "file:///android_asset/";

    private WebViewCallBack webViewCallBack;
    private WebView         webView;

    public CostomWebViewClient(WebView webView, WebViewCallBack webViewCallBack){
        this.webViewCallBack = webViewCallBack;
        this.webView = webView;
    }

    //https://developer.android.com/reference/android/webkit/WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView,%20java.lang.String)
    //官网上 对 shouldOverrideUrlLoading的 返回值有具体的解释 ===> true to cancel the current load, otherwise return false.
    //返回值为true:Webview中止加载Url。为false:Webview继续加载。
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        return super.shouldOverrideUrlLoading(view, request);
//    }

    /**
     * 支持电话、短信、邮件、地图跳转，跳转的都是手机系统自带的应用
     */
    private boolean handleLinked(String url) {
        if (url.startsWith(WebView.SCHEME_TEL)
//                || url.startsWith(SCHEME_SMS)
                || url.startsWith(WebView.SCHEME_MAILTO)
                || url.startsWith(WebView.SCHEME_GEO)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                webView.getContext().startActivity(intent);
            } catch (ActivityNotFoundException ignored) {
                ignored.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        //可根据url的信息，对请求进行拦截处理.eg:以下伪代码

        //if(好满足){
        //WebResourceResponse response = new WebResourceResponse("image/png", "utf-8", is);
        //renturn response;
        // }

        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (webViewCallBack != null) {
            webViewCallBack.onPageStarted(url);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith(CONTENT_SCHEME)) {
//            isReady = true;
        }
        if (webViewCallBack != null) {
            webViewCallBack.onPageFinished(url);
        }
    }


//    @TargetApi(21)
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//        return shouldInterceptRequest(view, request.getUrl().toString());
//    }
//
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//        return null;
//    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (webViewCallBack != null) {
            webViewCallBack.onReceivedError();
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        if (webViewCallBack != null) {
            webViewCallBack.onReceivedHttpError();
        }
    }

    //    @Override
//    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//        super.onReceivedError(view, errorCode, description, failingUrl);
//        Log.e(TAG, "webview error" + errorCode + " + " + description);
//        if (webViewCallBack != null) {
//            webViewCallBack.onError();
//        }
//    }

    //todo 待拓展
//    @Override
//    public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//        String channel = "";
//        if (!TextUtils.isEmpty(channel) && channel.equals("play.google.com")) {
//            final AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
//            String message = webView.getContext().getString(R.string.ssl_error);
//            switch (error.getPrimaryError()) {
//                case SslError.SSL_UNTRUSTED:
//                    message = webView.getContext().getString(R.string.ssl_error_not_trust);
//                    break;
//                case SslError.SSL_EXPIRED:
//                    message = webView.getContext().getString(R.string.ssl_error_expired);
//                    break;
//                case SslError.SSL_IDMISMATCH:
//                    message = webView.getContext().getString(R.string.ssl_error_mismatch);
//                    break;
//                case SslError.SSL_NOTYETVALID:
//                    message = webView.getContext().getString(R.string.ssl_error_not_valid);
//                    break;
//            }
//            message += webView.getContext().getString(R.string.ssl_error_continue_open);
//
//            builder.setTitle(R.string.ssl_error);
//            builder.setMessage(message);
//            builder.setPositiveButton(R.string.continue_open, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    handler.proceed();
//                }
//            });
//            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    handler.cancel();
//                }
//            });
//            final AlertDialog dialog = builder.create();
//            dialog.show();
//        } else {
//            handler.proceed();
//        }
//    }

}
