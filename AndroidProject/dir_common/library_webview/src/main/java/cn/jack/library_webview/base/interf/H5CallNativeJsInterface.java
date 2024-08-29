package cn.jack.library_webview.base.interf;

import android.os.Handler;
import android.webkit.JavascriptInterface;

import cn.jack.library_webview.base.h5callnative.WebCallNativeLisenter;
import cn.jack.library_webview.util.LogW;

import cn.jack.library_webview.base.h5callnative.WebCallNativeLisenter;
import cn.jack.library_webview.util.LogW;

/**
 * @创建者 Jack
 * @创建时间 2021/4/5 20:54
 * @描述 native跟web交互定义的接口
 * <p>
 * [将 JavaScript 代码绑定到 Android 代码](https://developer.android.com/guide/webapps/webview.html#BindingJavaScript)
 * <p>
 * 可以实现：JavaScript 代码可以调用 Android 代码中的方法
 */
public final class H5CallNativeJsInterface {

    public static String  AGREE_MENT = "webview";
    private final Handler mHandler   = new Handler();

    private WebCallNativeLisenter mCommandLisenter;

    public H5CallNativeJsInterface(WebCallNativeLisenter commandLisenter) {
        mCommandLisenter = commandLisenter;
    }

    //定义方法 === h5调用natice，只需要定义一个方法即可           参数cmd：需要跟H5开发共同定义好参数含义
    @JavascriptInterface
    public void post(final String cmd, final String param) {
        LogW.Companion.d(" h5调用native方法 post " + " cmd " + cmd + " param " + param);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mCommandLisenter != null) {
                        //从接口中解析 类型
                        mCommandLisenter.call(cmd, param);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
