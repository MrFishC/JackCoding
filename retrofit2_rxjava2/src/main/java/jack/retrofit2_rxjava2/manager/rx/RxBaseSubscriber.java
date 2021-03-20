package jack.retrofit2_rxjava2.manager.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.exception.DataNullException;
import jack.retrofit2_rxjava2.exception.TimeOutException;
import jack.retrofit2_rxjava2.exception.TokenInvalidException;
import jack.retrofit2_rxjava2.exception.UnloginException;
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
            onFailed((ApiException) e);
        }else if (e instanceof DataNullException) {
            //接口访问成功,但后台返回的数据为null,同样回调onSuccess方法          可以考虑通过 自定义转换器的方式来优化该位置
            onSuccess(null);
        }else{
            RxExceptionManager.getInstance().exceptionHandler(e);
        }

    }

    @Override
    public void onComplete() {

    }

    public abstract void onFailed(ApiException e);
    public abstract void onSuccess(T t);

}
