package com.jack.library_webview.process.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.jack.library_webview.process.BinderPoolImpl;
import com.jack.library_webview.util.LogW;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:27
 * @描述
 */
public class MainProcessService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
//        LogW.Companion.d("开启 了服务3 ");//这个日志未打印  具体原因不明
        System.out.println("开启 了服务3 ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        LogW.Companion.d("开启 了服务");//这个日志未打印  具体原因不明
        System.out.println("开启 了服务 onBind ");
        int pid = android.os.Process.myPid();
//        LogW.Companion.d("进程信息 " + pid);  //这个日志未打印  具体原因不明
        System.out.println("开启 了服务 进程信息 ");
        System.out.println("pid " + pid);
        return new BinderPoolImpl(this);
    }

}
