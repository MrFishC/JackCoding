package jack.wrapper.base.mvvm.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding3.view.RxView;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import cn.jack.library_common_business.loadsir.ViewStateLayout;
import cn.jack.library_common_business.loadsir.callback.CustomCallback;
import cn.jack.library_common_business.loadsir.callback.EmptyCallback;
import cn.jack.library_common_business.loadsir.callback.FailedCallback;
import cn.jack.library_common_business.loadsir.callback.LoadingCallback;
import cn.jack.library_common_business.loadsir.callback.TimeoutCallback;
import cn.jack.library_util.LogUtils;
import io.reactivex.functions.Consumer;
import jack.wrapper.base.mvvm.view.interf.ILoadSirLisenter;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;
import jack.wrapper.rxhelper.ViewRepeatClickLisenter;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-11
 * describe:fragment的基类,不需要实现状态布局的请继承该类
 */

public abstract class BaseFragment<V extends ViewDataBinding,VM extends BaseViewModel> extends BaseTopFragment implements ILoadSirLisenter {

    protected V        mBinding;
    protected VM       mViewModel;

    protected Activity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(mActivity == null){
            mActivity = getActivity();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //解除ViewModel生命周期感应
        if(mViewModel!=null){
            getLifecycle().removeObserver(mViewModel);
        }

        if(mBinding != null){
            mBinding.unbind();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();

        if(isRegisterLoadSir()){
            //监听VM层（状态布局属性的变化）
            setViewStateChangeLisenter();
        }

        //默认的初始化的顺序
        init();

    }

    protected void setViewStateChangeLisenter(){
        mViewModel.mUiStatesChange.observe(getViewLifecycleOwner(), new Observer<ViewStateLayout>() {
            @Override
            public void onChanged(ViewStateLayout stateLayout) {
                stateLayoutChange(stateLayout);
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
            mViewModel = (VM) createViewModel(this, modelClass);
        }

        //关联ViewModel
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

        //1.展示 加载对话框(带自定义标题)
        mViewModel.getUC().getShowDialogEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                //                showMDialog(title);
            }
        });

    }

    /**
     * 初始化根布局:返回界面layout的id
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 通BaseActivity的封装
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * @param fragment
     * @param cls
     * @param <T>
     * @return  mode实例
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    private LoadService mLoadService;

    protected void setLoadService(View rootView){
        if(isRegisterLoadSir()) {
            mLoadService = LoadSir.getDefault().register(rootView, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    dataReload();
                }
            });
        }
    }

    //view的防重复点击
    @SuppressLint("CheckResult")
    protected void handleViewRepeatClick(View view, ViewRepeatClickLisenter viewRepeatClickLisenter){

        RxView.clicks(view)
                .compose(bindToLifecycle())
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                        LogUtils.d(" 重复点击 " + view.getId() + " " + System.currentTimeMillis());

                        if(viewRepeatClickLisenter != null){
                            viewRepeatClickLisenter.repeatClick();
                        }
                    }
                });

    }

}
