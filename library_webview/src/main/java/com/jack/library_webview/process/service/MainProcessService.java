package com.jack.library_webview.process.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.jack.library_webview.process.BinderPoolImpl;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:27
 * @描述
 */
public class MainProcessService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BinderPoolImpl(this);
    }

}
