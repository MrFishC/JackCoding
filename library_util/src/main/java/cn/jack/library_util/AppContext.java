package cn.jack.library_util;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:全局的context
 */
public class AppContext {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public AppContext() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(final Context context) {
        if(context == null){
            throw new NullPointerException("context must be not null ...");
        }
        AppContext.sContext = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (sContext != null) {
            return sContext;
        }
        throw new NullPointerException("should be initialized in application");
    }
}
