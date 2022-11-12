package com.jack.library_webview.process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.jack.library_webview.IBinderPool;
import com.jack.library_webview.process.service.MainProcessService;
import com.jack.library_webview.util.LogW;

import java.util.concurrent.CountDownLatch;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:24
 * @描述
 */
public class BinderPoolManager {

    private        Context           mContext;
    //========================单例
    private static BinderPoolManager instance;

    private BinderPoolManager() {
        LogW.Companion.d("开启 了服务2 ");
        //服务没有被启动 ---> 待排查    具体原因是   ？？？？？？ 因为mContext：  带着问题查看源码
        //        mContext = context.getApplicationContext();
        //        initBinderPoolService(context.getApplicationContext());
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
     */

    //    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private InitBindServiceCall mInitBindServiceCall;
    public synchronized void initBinderPoolService(Context context,InitBindServiceCall initBindServiceCall) {
        mInitBindServiceCall = initBindServiceCall;
        Intent service = new Intent(context, MainProcessService.class);
        boolean b = context.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        LogW.Companion.d("开启 了服务 b = " + b);
        //        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        //        Intent service = new Intent(mContext, MainProcessService.class);
        //        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        //        try {
        //            mConnectBinderPoolCountDownLatch.await();
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
    }

    private IBinderPool       mIBinderPool;
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogW.Companion.d("开启 服务 onServiceConnected");
            //客户端拿到aidl句柄对象：得到Proxy对象（实体类，实现了定义的AIDL接口）：用来发送数据
            mIBinderPool = IBinderPool.Stub.asInterface(service);
            mInitBindServiceCall.success();

            LogW.Companion.d("开启 onServiceConnected mIBinderPool == null " + (mIBinderPool == null));

            //            mIBinderPool = IBinderPool.Stub.asInterface(service);
            //            try {
            //                mIBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            //            } catch (RemoteException e) {
            //                e.printStackTrace();
            //            }
            //            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogW.Companion.d("开启 服务 onServiceDisconnected");
        }
    };

    //    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {    // 6
    //        @Override
    //        public void binderDied() {
    //            mIBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
    //            mIBinderPool = null;
    //            initBinderPoolService();
    //        }
    //    };

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            LogW.Companion.d("开启 queryBinder mIBinderPool == null " + (mIBinderPool == null));
            if (mIBinderPool != null) {
                //调用方法---实际上是调用Proxy(实现IBinderPool)的queryBinder方法

                //--->即：开始Binder调用
                //--->触发mRemote.transact
                //--->触发Binder.onTransact（此时客户端线程挂起）
                //--->触发Stub（实现IBinderPool）的onTransact（此时客户端线程挂起，直到服务端有数据返回）
                //--->触发Stub（实现IBinderPool）的queryBinder方法
                //--->触发Stub（实现IBinderPool）实现类BinderPoolImpl的queryBinder方法[自行实现逻辑]

                //总结：最终调用BinderPoolImpl的queryBinder方法
                binder = mIBinderPool.queryBinder(binderCode);
                LogW.Companion.d("开启 queryBinder binder == null " + (binder == null));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    public interface InitBindServiceCall{
        void success();
    }

}
