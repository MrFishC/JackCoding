package com.jack.library_webview.process;

import android.content.Context;
import android.os.RemoteException;

import com.jack.library_webview.IWeb2NativeCallback;
import com.jack.library_webview.IWeb2NativeInterface;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:33
 * @描述
 */
public class IWebNativeInterfaceImpl extends IWeb2NativeInterface.Stub{
    private Context mContext;

    public IWebNativeInterfaceImpl(Context context) {
        mContext = context;
    }

    @Override
    public void handleWebAction(int level, String actionName, IWeb2NativeCallback callback) throws RemoteException {

    }

}
