package jack.wrapper.base.mvvm.viewModel.liveData;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:todo  自定义MutableLiveData的实现类(数据变化的时候更新UI),增加数据错误时处理;    需要更改
 * v层观察vm层,数据发生变化的时候,v层可知;
 * 继承BaseLiveData类,一共有两种情况,在BaseViewModel中创建的实现类是用于通用的基类view中,BaseViewModel的实现类中的BaseLiveData实现类,使用于具体的view层;
 *
 * 不了解LiveData的情况下,请参考:https://blog.csdn.net/zhuzp_blog/article/details/78871527
 */
public class BaseLiveData<T> extends MutableLiveData<T> {

    //setValue()要在主线程中调用
    @MainThread
    public void setValue(@Nullable T value) {
            super.setValue(value);
    }

    /**
     * 该方法也是调用setValue,只是value为null情况下被调用
     */
    @MainThread
    public void call() {
        setValue(null);
    }

}
