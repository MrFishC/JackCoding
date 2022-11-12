package com.jack.library_webview.dispatcher;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.webkit.WebView;
import com.google.gson.Gson;
import com.jack.library_webview.IWeb2NativeCallback;
import com.jack.library_webview.IWeb2NativeInterface;
import com.jack.library_webview.base.webview.BaseWebView;
import com.jack.library_webview.command.callback.CommandCallBack;
import com.jack.library_webview.command.manager.CommandsManager;
import com.jack.library_webview.process.BinderConstants;
import com.jack.library_webview.process.BinderPoolManager;
import com.jack.library_webview.util.LogW;
import com.jack.library_webview.util.MainLooper;
import com.jack.library_webview.util.SystemInfoUtil;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 9:57
 * @描述 初始化aidl，管理native跟h5之间交互的事件
 * <p>
 * 复习android艺术探索 ipc章节
 */
public class CommandDispatcher {
    private static CommandDispatcher instance;

    private Gson mGson = new Gson();

    private CommandDispatcher() {

    }

    public static CommandDispatcher getInstance() {
        if (instance == null) {
            synchronized (CommandDispatcher.class) {
                if (instance == null) {
                    instance = new CommandDispatcher();
                }
            }
        }
        return instance;
    }

    /**
     * 执行请求[H5页面调用native方法]
     *
     * @param context
     * @param cmd
     * @param cmd
     * @param webView
     */
    public void execute(Context context, int commandLevel, String cmd, String params, WebView webView) {
        LogW.Companion.d(" execute >>> " + "\n commandLevel " + commandLevel + "\n cmd " + cmd);

        //判断是不是主进程
        if (SystemInfoUtil.Companion.inMainProcess(context, android.os.Process.myPid())) {
            LogW.Companion.d(" execRemoteCommandRequest 1");
            execLocalRequest(context, commandLevel, cmd, params, webView);
        } else {
            //非主进程，需要IPC
            execCommandRequest(context, commandLevel, cmd, params, webView);
        }
    }

    private void execCommandRequest(Context context, int commandLevel, String cmd, String params, WebView webView) {
        LogW.Companion.d(" execRemoteCommandRequest 2");
        if (mIWeb2NativeInterface != null) {

            LogW.Companion.d(" execRemoteCommandRequest 3 ===");
            try {
                //4.根据多态，调用Proxy（实现了IWeb2NativeInterface）对象的handleWebAction
                //--->即：开始Binder调用
                //--->触发mRemote.transact
                //--->触发Binder.onTransact（此时客户端线程挂起）
                //--->触发Stub（实现IWeb2NativeInterface）.onTransact（此时客户端线程挂起，直到服务端有数据返回）
                //--->触发Stub（实现IWeb2NativeInterface）.handleWebAction方法
                //--->触发Stub（实现IWeb2NativeInterface）实现类IWebNativeInterfaceImpl的handleWebAction方法[自行实现逻辑]

                //此时，是从WebActivity所在的进程开始
                mIWeb2NativeInterface.handleWebAction(commandLevel, cmd, params, new IWeb2NativeCallback.Stub() {
                    @Override
                    public void onResult(int responseCode, String actionName, String response) throws RemoteException {
                        System.out.println(" onResult === ");
                        LogW.Companion.d(" onResult 1 " + "\n actionName " + actionName + "\n response " + response);
                        LogW.Companion.d(" onResult 2 进程" + android.os.Process.myPid());
                        handleCallback(responseCode, actionName, response, webView);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
                LogW.Companion.d(" RemoteException " + e.getMessage());
            }
        }
    }

    private void execLocalRequest(Context context, int commandLevel, String cmd, String params, WebView webView) {
        LogW.Companion.d(" execCommandRequest " + "\n cmd " + cmd + "\n param " + params);
        CommandsManager.getInstance().execCommand(context, commandLevel, cmd, params, new CommandCallBack() {
            @Override
            public void onResult(int status, String action, Object result) {
                //存在着数据转换
                handleCallback(status, action, mGson.toJson(result), webView);
            }
        });
    }

    private void handleCallback(final int status, final String actionName, final String response, final WebView webView) {
        LogW.Companion.d(" handleCallback " + "\n status " + status + "\n actionName " + actionName + "\n response " + response);
        MainLooper.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((BaseWebView) webView).handleCallback(response);
            }
        });
    }

    private IWeb2NativeInterface mIWeb2NativeInterface;

    /**
     * 初始化aidl
     */
    public void initAidlConnect(Context context) {
        if (mIWeb2NativeInterface != null) {
            LogW.Companion.d("开启了服务 准备 mIWeb2NativeInterface != null");
            return;
        }

        new Thread(() -> {
            //1.开启了服务，创建了BinderPoolImpl--->IBinderPool.Stub的实现类
            BinderPoolManager.getInstance().initBinderPoolService(context, new BinderPoolManager.InitBindServiceCall() {
                @Override
                public void success() {
                    //2.调用IBinderPool的queryBinder方法--->实际是调用了其实现类（IBinderPool.Stub）的实现类BinderPoolImpl的queryBinder方法--->最终：创建IWebNativeInterfaceImpl类
                    //即：IBinder iBinder =  new IWebNativeInterfaceImpl()
                    LogW.Companion.d("开启 了服务1 ");
                    IBinder iBinder = BinderPoolManager.getInstance().queryBinder(BinderConstants.BINDER_WEB_AILD);
                    LogW.Companion.d("开启 iBinder  == null " + (iBinder == null));
                    //3.客户端拿到aidl句柄：得到Proxy（实现了IWeb2NativeInterface）对象--->用来向服务端[通过方法调用]发送数据
                    mIWeb2NativeInterface = IWeb2NativeInterface.Stub.asInterface(iBinder);
                    LogW.Companion.d("开启 mIWeb2NativeInterface == null " + (mIWeb2NativeInterface == null));
                }
            });
        }).start();
    }

}
