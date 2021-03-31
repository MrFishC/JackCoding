package cn.jack.library_util;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 17:41
 * @描述 暂时简单封装，待update。
 */
public class LogUtils {

    private static final String LOGGER_TAG = " LogUtils ";

    private static boolean mIsDebug;

    public static LogUtils getInstance() {
        return LogUtils.Holder.INSTANCE;
    }

    private static class Holder {
        private static final LogUtils INSTANCE = new LogUtils();
    }

    public void init(boolean debug){
        mIsDebug = debug;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private LogUtils() {

    }

    // ================================

    public static void d(Object message){
        if(mIsDebug) {
            Logger.t(LOGGER_TAG).d(message);
        }
    }

    public static void i(String message){
        if(mIsDebug) {
            Logger.t(LOGGER_TAG).i(message);
        }
    }

    public static void w(String message){
        if(mIsDebug) {
            Logger.t(LOGGER_TAG).w(message);
        }
    }

    public static void e(String message){
        if(mIsDebug) {
            Logger.t(LOGGER_TAG).d(message);
        }
    }

    // ================================


    public static void d(String tag,Object message){
        if(mIsDebug) {
            Logger.t(tag).d(message);
        }
    }

    public static void i(String tag,String message){
        if(mIsDebug) {
            Logger.t(tag).i(message);
        }
    }

    public static void w(String tag,String message){
        if(mIsDebug) {
            Logger.t(tag).w(message);
        }
    }

    public static void e(String tag,String message){
        if(mIsDebug) {
            Logger.t(tag).d(message);
        }
    }

}
