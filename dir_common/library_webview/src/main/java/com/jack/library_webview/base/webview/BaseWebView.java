package com.jack.library_webview.base.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.jack.library_webview.base.wbclient.CostomWebChromeClient;
import com.jack.library_webview.base.wbclient.CostomWebViewClient;
import com.jack.library_webview.base.h5callnative.WebCallNativeLisenter;
import com.jack.library_webview.base.interf.H5CallNativeJsInterface;
import com.jack.library_webview.base.callback.WebViewCallBack;
import com.jack.library_webview.base.set.WebViewSettingManager;
import com.jack.library_webview.command.CommandConstants;
import com.jack.library_webview.request.ExecuteLisenter;
import com.jack.library_webview.util.LogW;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 20:51
 * @描述 除了配置方面，这个类几乎不用做改动。
 *
 * todo webview安全问题，待增加
 *  https://developer.android.com/guide/webapps/managing-webview
 */
public class BaseWebView extends WebView {

    private ExecuteLisenter mExecuteLisenter;

    public BaseWebView(Context context) {
        this(context,null);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("JavascriptInterface")
    public void initWebview(Context context, WebViewCallBack webViewCallBack) {
        WebViewSettingManager.getInstance().settings(this);

        //[处理网页导航](https://developer.android.com/guide/webapps/webview.html#HandlingNavigation)
        setWebViewClient(new CostomWebViewClient(this,webViewCallBack));

        setWebChromeClient(new CostomWebChromeClient());    //可自行拓展功能

        addJavascriptInterface(new H5CallNativeJsInterface(new WebCallNativeLisenter() {
            @Override
            public void call(String cmd,String param) {
                if(mExecuteLisenter != null){
                    LogW.Companion.d(" mExecuteLisenter  " + " cmd " + cmd + " param " + param);
                    mExecuteLisenter.executeRequest(context,mExecuteLisenter.getCommandLevel(CommandConstants.LEVEL_LOCAL),cmd,param,BaseWebView.this);
                }
            }
        }), H5CallNativeJsInterface.AGREE_MENT);
    }

    public void setExecuteLisenter(ExecuteLisenter executeLisenter) {
        mExecuteLisenter = executeLisenter;
    }

    //处理完毕，又回到了webview，将结果交给ExecuteLisenter实现类处理。
    public void handleCallback(String result) {
        if(mExecuteLisenter != null){
            mExecuteLisenter.handleCallback(result);
        }
    }

}
