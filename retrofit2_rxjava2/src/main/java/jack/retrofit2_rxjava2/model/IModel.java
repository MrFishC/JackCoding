package jack.retrofit2_rxjava2.model;

import io.reactivex.disposables.Disposable;

/**
 * @创建者 Jack
 * @创建时间 2021/3/31 14:25
 * @描述  将IModel定义在网络lib中
 */
public interface IModel {
    /**
     * View层销毁时,ViewModel销毁,对应Model销毁,对应Repository中,使用rxjava执行联网请求的时候防止内存泄露问题;
     */
    void onCleared();

    void addSubscribe(Disposable disposable);
}
