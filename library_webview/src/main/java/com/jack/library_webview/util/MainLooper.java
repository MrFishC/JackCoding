package com.jack.library_webview.util;

import android.os.Handler;
import android.os.Looper;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 11:15
 * @描述
 */
public class MainLooper extends Handler {
    private static MainLooper instance = new MainLooper(Looper.getMainLooper());

    protected MainLooper(Looper looper) {
        super(looper);
    }

    public static MainLooper getInstance() {
        return instance;
    }

    public static void runOnUiThread(Runnable runnable) {
        if(Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            instance.post(runnable);
        }

    }
}
