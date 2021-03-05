package jack.wrapper.base.mvvm.view.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import jack.wrapper.base.mvvm.view.IBaseView;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:activity的基类
 */

public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends BaseTopActivtiy implements IBaseView {

    protected V mBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBinding();

        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();

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
        mViewModel = initViewModel();

        //使用自定义的ViewModelFactory来创建ViewModel
        if (mViewModel == null) {
            Class modelClass;
            //Type:是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            Type type = getClass().getGenericSuperclass();  //获得带有泛型的父类 //其它:getSuperclass()获得该类的父类
            if (type instanceof ParameterizedType) {        //ParameterizedType参数化类型，即泛型
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];    //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }

            Log.d("名称 "," = " + modelClass.getName());

            mViewModel = (VM) createViewModel(this, modelClass);
        }

        //关联ViewModel       相当于通过<variable>标签指定类（然后在控件的属性值中就可以使用）[子类initVariableId()的返回的为BR.xxx,其中xxx必须和xml的<variable>标签一样]
        mBinding.setVariable(initVariableId(), mViewModel);

        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(mViewModel);
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
    public abstract int initContentView();

    /**
     * 初始化ViewModel的id
     *
     * 使用BR.xxx找到这个ViewModel的id;
     * @return BR的id (BR由系统生成)
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * 子类选择性重写initViewModel()方法，返回ViewModel对象;
     * 默认会创建VM类型的ViewModel,如果没有指定VM,则会创建BaseViewModel;
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

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
