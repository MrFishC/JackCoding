package jack.wrapper.base.mvvm.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import cn.jack.library_common_business.loadsir.ViewStateLayout;
import cn.jack.library_common_business.loadsir.callback.CustomCallback;
import cn.jack.library_common_business.loadsir.callback.EmptyCallback;
import cn.jack.library_common_business.loadsir.callback.FailedCallback;
import cn.jack.library_common_business.loadsir.callback.LoadingCallback;
import cn.jack.library_common_business.loadsir.callback.TimeoutCallback;
import jack.wrapper.base.mvvm.view.interf.ILoadSirLisenter;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:activity的基类
 *
 * update:
 *  BaseViewModel 需要传递泛型
 *      建议：取消BaseViewModel的泛型参数
 */

public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends BaseTopActivtiy implements ILoadSirLisenter {

    protected V mBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBinding();

        initImmersionBar();

        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();

        if(isRegisterLoadSir()){
            if(!isViewRegisterLoadSir()){
                setLoadService(this);
            }

            //监听VM层（状态布局属性的变化）
            setViewStateChangeLisenter();
        }

        //默认的初始化的顺序
        init();

    }

    /**
     * 模板设计模式
     */
    protected final void init() {
        prepareParam();
        prepareData();
        prepareListener();
    }

    /**
     * 初始化
     */
    private void initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.setContentView(this, initContentView());

        Class modelClass = null;
        //Type:是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        //获得带有泛型的父类 //其它:getSuperclass()获得该类的父类
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            //ParameterizedType参数化类型，即泛型
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
        }

        if(modelClass == null){
            //只要按照规则定义了泛型，则mViewModel不会为null
            throw new NullPointerException("VM must be define ...");
        }

        //ViewModel的初始化交给了父类，子类只需要按照规则定义泛型即可
        mViewModel = (VM) createViewModel(this, modelClass);

        //在基类中定义mBinding.setVariable(BR.xxx,y)相当于是约定，在布局中的layout-data-variable节点只要设定了name=viewModel，type=对应的子ViewModel
        //即实现databinding与ViewModel组件无缝协作
        //关联ViewModel       相当于通过<variable>标签指定类（然后在控件的属性值中就可以使用）[子类initVariableId()的返回的为BR.xxx,其中xxx必须和xml的<variable>标签一样]
        mBinding.setVariable(initVariableId(), mViewModel);

        //让ViewModel拥有View的生命周期感应:即mViewModel成为观察者
        getLifecycle().addObserver(mViewModel);

    }

    /**
     * ================================================
     * ui状态布局变化监听（VM通过livedata通知V层做出变化）*/
    protected void setViewStateChangeLisenter(){
        mViewModel.mUiStatesChange.observe(this, new Observer<ViewStateLayout>() {
            @Override
            public void onChanged(ViewStateLayout stateLayout) {
                stateLayoutChange(stateLayout);
            }
        });
    }

    private LoadService mLoadService;       //databinding跟loadsir是否可以关联起来？ 待思考  （目前应该不支持，暂时放下）
    //虽然状态布局在view变化，但是都是由数据驱动的，即m层代码 ---> vm --->v层监听vm的变化做出相应的改变

    //LoadSir的Convertor是个很好的功能，但在该项目不合适。            //不使用让整个页面都展示状态布局
    protected void setLoadService(Object target){
        mLoadService = LoadSir.getDefault().register(target, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                dataReload();
            }
        });
    }

    /**
     * 在View 中使用
     */
    protected void setLoadService(View view){
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                dataReload();
            }
        });
    }

    private void stateLayoutChange(ViewStateLayout stateLayout){
        switch (stateLayout){
            case LOADING:
                mLoadService.showCallback(LoadingCallback.class);
                System.out.println(" 状态信息 v LOADING ");
                break;
            case SUCCESS:
                mLoadService.showSuccess();
                System.out.println(" 状态信息 v SUCCESS ");
                break;
            case FAILED:
                mLoadService.showCallback(FailedCallback.class);
                System.out.println(" 状态信息 v FAILED ");
                break;
            case NET_ERROR:
                //todo
//                break;    //暂时跟time_out一样
            case TIME_OUT:
                mLoadService.showCallback(TimeoutCallback.class);
                System.out.println(" 状态信息 v NET_ERROR ");
                break;
            case CUSTOM:
                mLoadService.showCallback(CustomCallback.class);
                System.out.println(" 状态信息 v CUSTOM ");
                break;
            case EMPTY:
                mLoadService.showCallback(EmptyCallback.class);
                System.out.println(" 状态信息 v EMPTY ");
                break;
        }
    }

    /**
     * =====================================================================
     * 在viewmodel中执行逻辑的时候,通知view层更改ui的回调,公共部分
     */
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {

        //viewModel.getUC():获取对象UIChangeLiveData
        //UIChangeLiveData 继承自MutableLiveData
        //加载对话框显示
        mViewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
//                showDialog(title);

            }
        });

        //加载对话框消失
        mViewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                dismissDialog();
            }
        });

        //跳入新页面
        mViewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
//                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
//                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
//                startActivity(clz, bundle);

            }
        });

        //关闭界面
        mViewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });

        //关闭上一层
        mViewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //解除ViewModel生命周期感应
        if(mViewModel!=null){
            getLifecycle().removeObserver(mViewModel);
        }

        if(mBinding != null){
            mBinding.unbind();
        }

    }

    /**
     * 初始化根布局:返回界面layout的id
     *
     * @return 布局layout的id
     */
    public abstract @LayoutRes
    int initContentView();

    /**
     * 初始化ViewModel的id
     *
     * 使用BR.xxx找到这个ViewModel的id;
     * @return BR的id (BR由系统生成)
     */
    public abstract int initVariableId();

    /**
     *  通过 ViewModelProfiders#of(FragmentActivity) 来创建一个 ViewModelProvider，然后调用 get 方法创建出所需要的 mode实例
     * @param activity
     * @param cls
     * @param <T>
     * @return  mode实例
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

}
