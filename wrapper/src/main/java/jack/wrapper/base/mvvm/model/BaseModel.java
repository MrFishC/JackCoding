package jack.wrapper.base.mvvm.model;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import jack.retrofit2_rxjava2.model.IModel;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:服务于Repository,数据层基类,具体的Repository(数据仓库)需要实现该类
 *
 * 既然跟网络请求有关，难免跟net的lib有一定耦合（即实现IModel）
 *
 *
 */

public class BaseModel implements IModel {

    //管理RxJava的多个订阅（主要针对RxJava异步操作造成的内存泄漏），或者使用Disposable
    protected CompositeDisposable mCompositeDisposable;

    public BaseModel() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onCleared() {
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
