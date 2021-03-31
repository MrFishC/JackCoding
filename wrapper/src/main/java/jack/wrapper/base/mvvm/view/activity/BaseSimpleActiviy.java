package jack.wrapper.base.mvvm.view.activity;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * created by Jack
 * email:yucrun@163.com
 * describe:简易的activity实现该基类
 */
public abstract class BaseSimpleActiviy<V extends ViewDataBinding> extends BaseTopActivtiy {

    protected V mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, setLayoutRes());

        initImmersionBar();

        //默认的初始化的顺序
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

    protected abstract @LayoutRes
    int setLayoutRes();

}
