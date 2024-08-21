package cn.jack.library_webview.process;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import cn.jack.library_webview.IBinderPool;

/**
 * @创建者 Jack
 * @创建时间 2021/4/6 18:29
 * @描述
 */
public class BinderPoolImpl extends IBinderPool.Stub{
    //IBinderPool.Stub:抽象类，继承自Binder，实现了IBinderPool定义的AIDL接口
    private Context context;

    public BinderPoolImpl(Context context) {
        this.context = context;
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BinderConstants.BINDER_WEB_AILD: {
                binder = new IWebNativeInterfaceImpl(context);
                break;
            }
            default:
                break;
        }
        return binder;
    }

}
