package jack.wrapper.base.mvvm.viewModel;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import java.util.HashMap;
import java.util.Map;
import cn.jack.library_common_business.loadsir.ViewStateLayout;
import jack.retrofit2_rxjava2.exception.ErrorStatusInfo;
import jack.wrapper.base.contract.IBaseViewStateContract;
import jack.wrapper.base.mvvm.model.BaseModel;
import jack.wrapper.base.mvvm.viewModel.liveData.BaseUIChangeLiveData;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:
 *      ViewModel层的基类（继承AndroidViewModel）
 *      注意：继承自AndroidViewModel而不是ViewModel,原因在于AndroidViewModel构造传入了全局的Application.
 *      ViewModel层不能持有activity或者fragment的引用,但有些情况需要使用到context,所以选择继承AndroidViewModel;
 *
 *      实现 ILifecycleCallback接口,即实现DefaultLifecycleObserver(jdk1.8实现DefaultLifecycleObserver,jdk1.7
 *实现LifecycleObserver),为了实现View层与ViewModel层生命周期同步;
 *
 * update(21-03-19):
 *  在该类中加入新的泛型参数
 *      L extends IBaseContract.IBridge（已去掉）
 *
 * update(21-03-30):
 *      使用状态布局
 *      将IBaseContract更改为IBaseViewStateContract
 */

public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements ILifecycleCallback
        , IBaseViewStateContract      //实现该接口，避免其子类再次重写IBaseViewStateContract中的方法
{

    //ViewModel层持有Model层引用,Model的具体实现类为自定义的xxxRepository(数据仓库类)
    protected M mModel;

    //这里可以封装一个LiveData的实现类,View层发生改变的观察者,封装观察一些通用的ui变化的观察者.具体每一个界面的单独变化在具体的BaseViewModel实现类中添加
    //让ViewModel层持有LiveData层引用,当数据发生变化的时候,view层通过设置对ViewModel的监听能够知道数据发生改变
    private BaseUIChangeLiveData mUIChangeLiveData;

    //处理Loadsir框架的问题
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final int DELAY_TIME = 1000;

    public MutableLiveData<ViewStateLayout> mUiStatesChange = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    //若要对mModel进行赋值，子类必须要调用父类=该构造方法
//    public BaseViewModel(@NonNull Application application, M model) {
//        super(application);
//        this.mModel = model;
//    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    @Override
    protected void onCleared() {
        //何时会执行该方法,点击下方查看父类注释
        super.onCleared();
        //viewmodle层执行onCleared方法时,执行BaseModel的onCleared方法,即取消所有异步任务
        if (mModel != null) {
            mModel.onCleared();
        }
    }

    // todo ------------------------------------------------  公共部分,v层通过观察vm层,自行决定是否需要调用v层的方法  --------------------------------------------------------------
    public BaseUIChangeLiveData getUC() {
        if (mUIChangeLiveData == null) {
            mUIChangeLiveData = BaseUIChangeLiveData.getInstance();
        }
        return mUIChangeLiveData;
    }


    public void showDialog() {
        //具体标题内容,通过传递参数的形式更改
        showDialog("请稍后...");
    }

    /**
     * 显示对话框
     * @param title
     */
    public void showDialog(String title) {
        if(mUIChangeLiveData != null)
        mUIChangeLiveData.getShowDialogEvent().postValue(title);
    }

    /**
     * 关闭对话框   todo
     */
    public void dismissDialog() {
        //
//        mUIChangeLiveData.getDismissDialogEvent().call();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        if(mUIChangeLiveData != null)
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        mUIChangeLiveData.getStartActivityEvent().postValue(params);
    }

    /**
     * 关闭界面
     */
    public void finish() {
//        mUIChangeLiveData.getFinishEvent().call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
//        mUIChangeLiveData.getOnBackPressedEvent().call();
    }

    @Override
    public void loading() {
        System.out.println(" 状态信息 vm loading");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.LOADING);
            }
        }, DELAY_TIME);
    }

    @Override
    public void loadSuccess() {
        System.out.println(" 状态信息 vm loadSuccess");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.SUCCESS);
            }
        }, DELAY_TIME);
    }

    @Override
    public void loadEmpty() {
        System.out.println(" 状态信息 vm loadEmpty");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.EMPTY);
            }
        }, DELAY_TIME);
    }

    @Override
    public void loadFailed(ErrorStatusInfo errorStatusInfo) {
        System.out.println(" 状态信息 vm loadFailed");

        //todo 根据不同状态进行设置  即可

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.FAILED);
            }
        }, DELAY_TIME);
    }

    @Override
    public void timeOut() {
        System.out.println(" 状态信息 vm timeOut");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.TIME_OUT);
            }
        }, DELAY_TIME);
    }

    @Override
    public void netError() {
        System.out.println(" 状态信息 vm netError");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.NET_ERROR);
            }
        }, DELAY_TIME);
    }

    @Override
    public void custom() {
        System.out.println(" 状态信息 vm custom");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mUiStatesChange.setValue(ViewStateLayout.CUSTOM);
            }
        }, DELAY_TIME);
    }

    @Override
    public void showToast(String toastMsg) {

    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
    }
}
