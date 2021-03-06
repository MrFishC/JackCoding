package jack.wrapper.base.mvvm.viewModel.liveData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:当数据发生变化的时候,view层通过设置对ViewModel的监听能够知道数据发生改变
 * MutableLiveData是LiveData的子类
 *
 * update：
 *  v层观察vm层
 */
public class BaseUIChangeLiveData<T extends Object> extends BaseLiveData<T> {

    private BaseUIChangeLiveData mBaseUIChangeLiveData;

    /**
     * 单例 - 静态内部类
     */
    public static BaseUIChangeLiveData getInstance() {
        return BaseUIChangeLiveData.Holder.INSTANCE;
    }

    private static class Holder {
        private static final BaseUIChangeLiveData INSTANCE = new BaseUIChangeLiveData();
    }

    private BaseUIChangeLiveData() {

    }

    /**
     * =========================================
     */

    private BaseUIChangeLiveData<String>              mShowDialogEvent;
    private BaseUIChangeLiveData<Void>                mDismissDialogEvent;
    private BaseUIChangeLiveData<Map<String, Object>> mStartActivityEvent;
    private BaseUIChangeLiveData<Void>                mFinishEvent;
    private BaseUIChangeLiveData<Void>                mOnBackPressedEvent;

    /**
     * 展示对话框事件对象
     * @return
     */
    public BaseLiveData<String> getShowDialogEvent() {
        return mShowDialogEvent = createLiveData(mShowDialogEvent);
    }

    /**
     * 关闭对话框事件对象
     * @return
     */
    public BaseLiveData<Void> getDismissDialogEvent() {
        return mDismissDialogEvent = createLiveData(mDismissDialogEvent);
    }

    /**
     * 跳转activtiy事件对象
     * @return
     */
    public BaseLiveData<Map<String, Object>> getStartActivityEvent() {
        return mStartActivityEvent = createLiveData(mStartActivityEvent);
    }

    /**
     * 关闭界面事件对象
     * @return
     */
    public BaseLiveData<Void> getFinishEvent() {
        return mFinishEvent = createLiveData(mFinishEvent);
    }

    /**
     * 点击返回按钮事件对象
     * @return
     */
    public BaseLiveData<Void> getOnBackPressedEvent() {
        return mOnBackPressedEvent = createLiveData(mOnBackPressedEvent);
    }

    private <E> BaseUIChangeLiveData<E> createLiveData(BaseUIChangeLiveData<E> liveData) {
        if (liveData == null) {
            liveData = new BaseUIChangeLiveData<E>();
        }
        return liveData;
    }

    @Override
    public void observe(@NotNull LifecycleOwner owner,@NotNull Observer observer) {
        super.observe(owner, observer);
    }
}
