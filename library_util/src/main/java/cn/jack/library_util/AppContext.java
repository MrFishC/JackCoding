package cn.jack.library_util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:全局的context
 */
public class AppContext {

    @SuppressLint("StaticFieldLeak")
    private static Context     sContext;

    private static Application sApplication;

    public AppContext() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(final Context context,final Application application){
        if(context == null){
            throw new NullPointerException("context must be not null ...");
        }

        if(application == null){
            throw new NullPointerException("application must be not null ...");
        }

        AppContext.sContext = context.getApplicationContext();
        AppContext.sApplication = application;
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

    /**
     * 获取Application
     *
     */
    public static Application getApplication() {
        if (sApplication != null) {
            return sApplication;
        }
        throw new NullPointerException("should be initialized in application");
    }
}
