package jack.wrapper.base.app;

import android.app.Activity;

import java.util.Stack;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:
 *  管理activity和fragment
 *  单例模式,建议使用静态内部类
 */
public class AppManager {

    /**
     * 私有构造
     */
    private AppManager() {

    }

    /** 对外提供公共的访问方法 */
    public static AppManager getAppManager() {
        return AppManagerHolder.INSTANCE;
    }

    /** 写一个静态内部类，里面实例化外部类 */
    private static class AppManagerHolder {
        private static final AppManager INSTANCE = new AppManager();
    }

    private static Stack<Activity> sActivityStack;

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<Activity>();
        }
        sActivityStack.add(activity);
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
        }
    }

    /**
     * 获取sActivityStack
     * @return
     */
    public static Stack<Activity> getActivityStack() {
        return sActivityStack;
    }

    /**
     * 是否有activity
     */
    public boolean isActivity() {
        if (sActivityStack != null) {
            return !sActivityStack.isEmpty();
        }
        return false;
    }
}
