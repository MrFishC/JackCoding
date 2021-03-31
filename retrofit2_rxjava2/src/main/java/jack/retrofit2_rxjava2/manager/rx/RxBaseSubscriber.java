package jack.retrofit2_rxjava2.manager.rx;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.exception.DataNullException;
import jack.retrofit2_rxjava2.exception.TimeOutException;
import jack.retrofit2_rxjava2.model.IModel;
import jack.retrofit2_rxjava2.util.net.NetCheckHelper;

/**
 * <p>描述：订阅的基类</p>
 *
 * 将IModel放在RxBaseSubscriber中，可以更换处理内存泄露的方式，使得BaseModel子类在使用网络请求框架时，调用更简洁
 */
public abstract class RxBaseSubscriber<T> implements Observer<T> {

    private IModel mIModel;

    public RxBaseSubscriber(IModel iModel) {
        mIModel = iModel;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

        //判断是否有网络
        if (!NetCheckHelper.getInstance().isNetworkConnected()) {

            // 飞行模式下，模拟器 这里的代码都没有执行 。手机可以  （暂时不深究原因）
            //            System.out.println(" onStart  1");

            onNetError();
            onComplete();
        }

        if(mIModel != null){
            mIModel.addSubscribe(d);
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
        }else if (e instanceof TimeOutException) {
            onTimeOut();
        }else if (e instanceof DataNullException) {
            //接口访问成功,但后台返回的数据为null,同样回调onSuccess方法          可以考虑通过 自定义转换器的方式来优化该位置（待优化处理）
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

    public void onTimeOut(){        //任何封装都不是完美的（待有更优的封装方式后替换）

    }
    public void onNetError(){

    }

}