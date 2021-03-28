package jack.wrapper.base.mvvm.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @创建者 Jack
 * @创建时间 2021/3/8 15:03
 * @描述
 */
public abstract class BaseSimpleFragment<V extends ViewDataBinding> extends BaseTopFragment {
    protected V mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.inflate(inflater, setLayoutRes(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        initImmersionBar();

        init();
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

}
