package jack.retrofit2_rxjava2.cache;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @创建者 Jack
 * @创建时间 2021/4/2 14:22
 * @描述
 */
public class RxPreLoader<T> {

    private BehaviorSubject<T> mTBehaviorSubject;
    private Disposable mDisposable;

    public RxPreLoader(T defaultValue) {
        mTBehaviorSubject = BehaviorSubject.createDefault(defaultValue);
    }

    public void publish(T object) {
        mTBehaviorSubject.onNext(object);
    }

    public Disposable subscribe(Consumer onNext){
        mDisposable = mTBehaviorSubject.subscribe(onNext);
        return mDisposable;
    }

    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    public BehaviorSubject<T> getCacheDataSubject() {
        return mTBehaviorSubject;
    }

    public T getLastCacheData(){
        return mTBehaviorSubject.getValue();
    }

}

