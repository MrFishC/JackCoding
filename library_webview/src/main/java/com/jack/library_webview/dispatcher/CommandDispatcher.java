package com.jack.library_webview.dispatcher;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.webkit.WebView;

import com.jack.library_command.command.callback.CommandCallBack;
import com.jack.library_command.command.constants.CommandConstants;
import com.jack.library_command.command.manager.CommandsManager;
import com.jack.library_webview.IWeb2NativeInterface;
import com.jack.library_webview.base.webview.BaseWebView;
import com.jack.library_webview.process.BinderConstants;
import com.jack.library_webview.process.BinderPoolManager;
import cn.jack.library_util.MainLooper;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 9:57
 * @描述 初始化aidl，管理native跟h5之间交互的事件
 */
public class CommandDispatcher {
    private static CommandDispatcher instance;

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
     * 执行请求
     * @param context
     * @param commandLevel
     * @param cmd
     * @param webView
     */
    public void execute(Context context, int commandLevel, String cmd, WebView webView){
        System.out.println(" execute  " + commandLevel);

        //检查执行本地命令还是远端的命令
        if(commandLevel == CommandConstants.LEVEL_LOCAL){
            //1.本地(不需要进程间通讯)
            execCommandRequest(context,commandLevel,cmd,webView);
        }else if(commandLevel == CommandConstants.LEVEL_REMOTE){
            //2.远端(进程间通讯)

        }
    }

    private void execCommandRequest(final Context context, final  int commandLevel, final  String cmd, final WebView webView) {
        System.out.println(" execCommandRequest 1 ");
        CommandsManager.getInstance().execCommand(context, commandLevel, cmd, new CommandCallBack() {
            @Override
            public void onResult(int status, String action, Object result) {
                System.out.println(" execCommandRequest 2 ");
                //存在着数据转换
                System.out.println(" 存在着数据转换 ");
                handleCallback(status,action,"",webView);
            }
        });
    }

    private void handleCallback(final int status, final String actionName, final String response, final WebView webView) {
        System.out.println(" execCommandRequest 3 ");
        MainLooper.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //经过一系列判断
                System.out.println(" 经过一系列判断 ");
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
            return;
        }

        new Thread(()-> {
            BinderPoolManager.getInstance().initBinderPoolService(context);
            IBinder iBinder = BinderPoolManager.getInstance().queryBinder(BinderConstants.BINDER_WEB_AILD);
            mIWeb2NativeInterface = IWeb2NativeInterface.Stub.asInterface(iBinder);
        }).start();

    }
}
