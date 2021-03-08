package jack.retrofit2_rxjava2.manager.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.exception.DataNullException;
import jack.retrofit2_rxjava2.exception.TimeOutException;
import jack.retrofit2_rxjava2.exception.TokenInvalidException;
import jack.retrofit2_rxjava2.util.net.NetCheckHelper;

/**
 * <p>描述：订阅的基类</p>
 */
public abstract class RxBaseSubscriber<T> extends DisposableObserver<T> {

    public RxBaseSubscriber() {
    }

    @Override
    protected void onStart() {
        if (!NetCheckHelper.getInstance().isNetworkConnected()) {
            if(!isDisposed()){
                dispose();
            }
            onComplete();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public final void onError(Throwable e) {

        if (e instanceof ApiException) {
            onError((ApiException) e);
        }else if (e instanceof TimeOutException) {
            showTips("连接超时");
        }else if (e instanceof TokenInvalidException) {
            //...

        }else if (e instanceof DataNullException) {
            //接口访问成功,但后台返回的数据为null,同样回调onSuccess方法
            onSuccess(null);
        }else {
            showTips("网络请求超时");
        }

    }

    @Override
    public void onComplete() {

    }

    private void showTips(String tip){

    }

    public abstract void onError(ApiException e);
    public abstract void onSuccess(T t);

}
