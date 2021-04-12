package com.jack.library_webview.base.interf;

import android.webkit.JavascriptInterface;

import com.jack.library_webview.base.h5callnative.WebCallNativeLisenter;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 20:54
 * @描述 native跟web交互定义的接口
 *
 * [将 JavaScript 代码绑定到 Android 代码](https://developer.android.com/guide/webapps/webview.html#BindingJavaScript)
 *
 * 可以实现：JavaScript 代码可以调用 Android 代码中的方法
 *
 */
public final class H5CallNativeJsInterface {

    public static String AGREE_MENT = "webview";

    private WebCallNativeLisenter mCommandLisenter;

    public H5CallNativeJsInterface(WebCallNativeLisenter commandLisenter) {
        mCommandLisenter = commandLisenter;
    }

    //定义方法 === h5调用natice，只需要定义一个方法即可
    @JavascriptInterface
    public void post(final String cmd, final String param) {
        System.out.println(" h5调用native方法 post ");
//    public void callNative(final String msg) {
        if(mCommandLisenter != null){
//            mCommandLisenter.call(" cmd " + cmd + " " + param);
            //从接口中解析 类型
            mCommandLisenter.call(cmd);
        }
    }
}
