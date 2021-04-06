package com.jack.library_webview.process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.jack.library_webview.IBinderPool;
import com.jack.library_webview.IWeb2NativeInterface;
import com.jack.library_webview.process.service.MainProcessService;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:24
 * @描述
 */
public class BinderPoolManager {

    private Context mContext;
    //========================单例
    private static BinderPoolManager instance;

    private BinderPoolManager() {
    }

    public static BinderPoolManager getInstance() {

        if (instance == null) {
            synchronized (BinderPoolManager.class) {
                if (instance == null) {
                    instance = new BinderPoolManager();
                }
            }
        }
        return instance;
    }

    //========================初始化 IWeb2NativeInterface.Stub
    /**
     * 连接Service            获取mIWeb2NativeInterface接口
     *
     */
    public synchronized void initBinderPoolService(Context context) {
        mContext = context;
        Intent service = new Intent(mContext, MainProcessService.class);
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
    }

    private IBinderPool mIBinderPool;
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBinderPool = IBinderPool.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mIBinderPool != null) {
                binder = mIBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

}
