package jack.wrapper.base.mvvm.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import android.os.Bundle;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import jack.wrapper.base.contract.IBaseContract;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;
import jack.wrapper.base.mvvm.model.BaseModel;

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
 *      L extends IBaseContract.IBridge
 *
 */

//public class BaseViewModel<M extends BaseModel,L extends IBaseContract.IBridge> extends AndroidViewModel implements ILifecycleCallback,IBaseContract.IBridge{
public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements ILifecycleCallback
        ,IBaseContract
{

    //ViewModel层持有Model层引用,Model的具体实现类为自定义的xxxRepository(数据仓库类)
    protected M mModel;

    //这里可以封装一个LiveData的实现类,View层发生改变的观察者,封装观察一些通用的ui变化的观察者.具体每一个界面的单独变化在具体的BaseViewModel实现类中添加
    //让ViewModel层持有LiveData层引用,当数据发生变化的时候,view层通过设置对ViewModel的监听能够知道数据发生改变
    private UIChangeLiveData mUIChangeLiveData;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mModel = null;
    }

    //若要对mModel进行赋值，子类必须要调用父类=该构造方法
    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.mModel = model;
    }

    /**
     * 调用BaseModel中的addSubscribe方法,处理rxjava的内存泄露
     * @param disposable
     * 备注： 去掉 该方法
     */
    protected void addSubscribe(Disposable disposable) {
        if (mModel == null) {
            throw new NullPointerException("Create ViewModel with ViewModelFactory with Model");
        }
        mModel.addSubscribe(disposable);
    }

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
    public UIChangeLiveData getUC() {
        if (mUIChangeLiveData == null) {
            mUIChangeLiveData = new UIChangeLiveData();
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
        System.out.println(" loading ");
    }

    @Override
    public void loadFinished() {
        System.out.println(" loadFinished ");
    }

    @Override
    public void loadFailed() {
        System.out.println(" loadFailed ");
    }

    @Override
    public void showToast(String toastMsg) {
        System.out.println(" showToast ");
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
    }
}
