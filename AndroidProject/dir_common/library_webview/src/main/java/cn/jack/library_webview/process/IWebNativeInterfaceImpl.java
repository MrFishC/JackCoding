package cn.jack.library_webview.process;

import android.content.Context;
import android.os.RemoteException;
import com.google.gson.Gson;
import cn.jack.library_webview.IWeb2NativeCallback;
import cn.jack.library_webview.IWeb2NativeInterface;
import cn.jack.library_webview.command.callback.CommandCallBack;
import cn.jack.library_webview.command.manager.CommandsManager;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:33
 * @描述
 */
public class IWebNativeInterfaceImpl extends IWeb2NativeInterface.Stub {
    //IWeb2NativeInterface.Stub:抽象类，继承自Binder，实现了IWeb2NativeInterface定义的AIDL接口

    private Context mContext;

    public IWebNativeInterfaceImpl(Context context) {
        mContext = context;
    }

    @Override
    public void handleWebAction(int level, String cmd, String params, IWeb2NativeCallback callback) throws RemoteException {
        System.out.println(" handleWebAction level " + level + " cmd " + cmd + " params " + params + " callback " + callback);

        CommandsManager.getInstance().execCommand(mContext, level, cmd, params, new CommandCallBack() {
            @Override
            public void onResult(int status, String action, Object result) {
                if (callback != null) {
                    try {
                        System.out.println(" callback === ");
                        callback.onResult(status, cmd, new Gson().toJson(result));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
