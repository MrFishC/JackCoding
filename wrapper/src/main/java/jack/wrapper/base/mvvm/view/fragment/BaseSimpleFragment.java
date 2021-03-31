package jack.wrapper.base.mvvm.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import cn.jack.library_common_business.loadsir.ViewStateLayout;
import cn.jack.library_common_business.loadsir.callback.CustomCallback;
import cn.jack.library_common_business.loadsir.callback.EmptyCallback;
import cn.jack.library_common_business.loadsir.callback.FailedCallback;
import cn.jack.library_common_business.loadsir.callback.LoadingCallback;
import cn.jack.library_common_business.loadsir.callback.TimeoutCallback;
import jack.wrapper.base.mvvm.view.interf.ILoadSirLisenter;

/**
 * @创建者 Jack
 * @创建时间 2021/3/8 15:03
 * @描述
 */
public abstract class BaseSimpleFragment<V extends ViewDataBinding> extends BaseTopFragment implements ILoadSirLisenter {
    protected V mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.inflate(inflater, setLayoutRes(), container, false);

        if(isRegisterLoadSir()) {                   //目标：子类自由控制，让整个布局还是指定的view实现状态布局。
            if(isViewRegisterLoadSir()){
                return mBinding.getRoot();
            }else {
                setLoadService(mBinding.getRoot());
                //整个布局页面都会使用状态布局
                return mLoadService.getLoadLayout();
            }
        }else {
            return mBinding.getRoot();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private Handler mHandler   = new Handler(Looper.getMainLooper());
    private final int     DELAY_TIME = 1000;

    //由于BaseSimpleFragment没有用到mvvm架构，故需要在view层直接调用该方法
    protected void setViewStateChangeLisenter(ViewStateLayout stateLayout){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewStateChange(stateLayout);
            }
        },DELAY_TIME);
    }

    private void viewStateChange(ViewStateLayout stateLayout){
        if(mLoadService == null){
            return;
        }

        switch (stateLayout){
            case LOADING:
                mLoadService.showCallback(LoadingCallback.class);
                break;
            case SUCCESS:
                mLoadService.showSuccess();
                break;
            case FAILED:
                mLoadService.showCallback(FailedCallback.class);
                break;
            case NET_ERROR:
                //                break;    //暂时
            case TIME_OUT:
                mLoadService.showCallback(TimeoutCallback.class);
                break;
            case CUSTOM:
                mLoadService.showCallback(CustomCallback.class);
                break;
            case EMPTY:
                mLoadService.showCallback(EmptyCallback.class);
                break;
        }
    }

    /**
     * 模板设计模式
     */
    protected final void init() {
        //初始化参数
        prepareParam();
        //初始化数据
        prepareData();
        //设置监听
        prepareListener();
    }

    protected abstract int setLayoutRes();

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

}
