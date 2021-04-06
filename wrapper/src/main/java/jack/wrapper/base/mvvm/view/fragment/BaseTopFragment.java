package jack.wrapper.base.mvvm.view.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.trello.rxlifecycle3.components.support.RxFragment;
import cn.jack.library_util.AppContext;
import jack.wrapper.base.mvvm.view.interf.IBaseView;
import jack.wrapper.base.mvvm.view.interf.IStatusSwitchLisenter;

/**
 * @创建者 Jack
 * @创建时间 2021/3/4 18:20
 * @描述
 */
abstract class BaseTopFragment extends RxFragment implements IBaseView , IStatusSwitchLisenter , SimpleImmersionOwner {

    protected Application mApplication;

    //Fragment的View加载完毕的标记
    private boolean mIsViewCreated;

    //Fragment对用户可见的标记
    private boolean mIsUIVisible;

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    //若不实现ImmersionFragment，则BaseTopFragment的子类调用ImmersionBar等api将无效
    @Override
    public void initImmersionBar() {
        if(isBlack()){
            ImmersionBar
                    .with(this)
                    .statusBarDarkFont(true)
                    .init();
        }else {
            ImmersionBar
                    .with(this)
                    .init();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = AppContext.getApplication();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewCreated = true;
        lazyLoad();
    }

    //空实现部分方法,方便子类选择性实现
    //初始化参数
    @Override
    public void prepareParam() {

    }

    //初始化数据
    @Override
    public void prepareData() {

    }

    //设置监听
    @Override
    public void prepareListener() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            mIsUIVisible = true;
            lazyLoad();
        } else {
            mIsUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (mIsViewCreated && mIsUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            mIsViewCreated = false;
            mIsUIVisible = false;
        }
    }

    //需要懒加载的子类则自行重写该方法
    protected void loadData(){

    }
}