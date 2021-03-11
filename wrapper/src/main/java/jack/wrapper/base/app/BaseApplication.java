package jack.wrapper.base.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import cn.jack.library_image.glide.GlideManager;
import cn.jack.library_image.image.ImageManager;
import cn.jack.library_util.AppContext;
import jack.wrapper.BuildConfig;
import timber.log.Timber;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:在宿主app中新建application的子类实现该基类
 */

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        checkApplication();
    }

    /**
     * 检查当前进程,确保application只初始化一次,提高性能
     * 若不了解,请参考,https://blog.csdn.net/jason0539/article/details/45555671
     */
    private void checkApplication() {
        String processName = getApplocationProcessName(this);
        if (processName != null) {
            if (processName.equals(this.getPackageName())) {
                init();
            }
        }
    }

    /**
     * 子类重写该方法可以执行额外的初始化工作,若没有额外的初始化工作,直接在AndroidManiest.xml中配置该application;
     */
    protected void init() {
        setApplication(this);
    }

    /**
     * 获取进程名称
     *
     * @param context 上下文
     * @return 当前应用的名称
     */
    private String getApplocationProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    /**
     * 主工程继承BaseApplication时，需要手动调用setApplication方法初始化BaseApplication
     *
     * @param application application
     */
    public synchronized void setApplication(@NonNull Application application) {

        //初始化全局的context
        AppContext.init(application);

        initArouter();

        initImageLoader();

        //全局配置 todo  需要更改  建议更改在子类中去实现
//        GlobalConfig.init(application)
//                .withApiHost("http://192.168.1.164:8082/course-teacher/")
//                .withIsReleased(false)                  //使用动态的方式更改
//                .configure();

        //DEBUG模式下打印日志
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
    }

    private void initImageLoader() {
        GlideManager glideManager = new GlideManager.Builder().create();
        ImageManager.getInstance().init(glideManager);
    }

    private void initArouter() {
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

}
