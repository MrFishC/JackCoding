package cn.jack.lib_common.arouter_interceptor

import android.content.Context
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.jack.library_webview.util.LogW

/**
 * @创建者 Jack
 * @创建时间 2022/11/11 20:19
 * @描述 arouter拦截器
 * 业务场景：在加载H5页面之前判断token是否失效
 */
@Interceptor(priority = 7)
class WebLoadInterceptor : IInterceptor {
    /**
     * 该方法运行在arouter的线程池中
     */
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        logInfo("拦截 === 拦截操作开始了啊")
        if (RouterPathActivity.Web.PAGER_WEB == postcard.path) {
            //加载H5页面之前，判断token是否失效，若失效则跳转到登录页面先登录
            logInfo("拦截 === 啊进来了")

            //可以在这里请求接口 token失效的情况下，重新登录 再跳转H5页面

            callback.onContinue(postcard);

            // 这里的弹窗仅做举例，代码写法不具有可参考价值  参考arouter官方demo
            //            final AlertDialog.Builder ab = new AlertDialog.Builder(postcard.con);
            //            ab.setCancelable(false);
            //            ab.setTitle("温馨提醒");
            //            ab.setMessage("想要跳转到Test4Activity么？(触发了\"/inter/test1\"拦截器，拦截了本次跳转)");
            //            ab.setNegativeButton("继续", new DialogInterface.OnClickListener() {
            //                @Override
            //                public void onClick(DialogInterface dialog, int which) {
            //                    callback.onContinue(postcard);
            //                }
            //            });
            //            ab.setNeutralButton("算了", new DialogInterface.OnClickListener() {
            //                @Override
            //                public void onClick(DialogInterface dialog, int which) {
            //                    callback.onInterrupt(null);
            //                }
            //            });
            //            ab.setPositiveButton("加点料", new DialogInterface.OnClickListener() {
            //                @Override
            //                public void onClick(DialogInterface dialog, int which) {
            //                    postcard.withString("extra", "我是在拦截器中附加的参数");
            //                    callback.onContinue(postcard);
            //                }
            //            });
            //
            //            MainLooper.runOnUiThread(new Runnable() {
            //                @Override
            //                public void run() {
            //                    ab.create().show();
            //                }
            //            });
        } else {
            callback.onContinue(postcard)
        }
    }

    override fun init(context: Context) {
        logInfo(WebLoadInterceptor::class.java.name + "初始化成功")
    }

    private fun logInfo(msg: String) {
        LogW.Companion.d(msg)
    }
}