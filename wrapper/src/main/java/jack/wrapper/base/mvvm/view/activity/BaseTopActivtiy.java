package jack.wrapper.base.mvvm.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import jack.wrapper.R;
import jack.wrapper.base.mvvm.view.IBaseView;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-14
 * describe:BaseActivity和BaseStateActivity的父类,去掉public,只能由BaseActivity和BaseStateActivity继承
 */
abstract class BaseTopActivtiy extends AppCompatActivity implements IBaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (injectARouter()) {
            ARouter.getInstance().inject(this);
        }

        //todo 事件总线的封装
        //todo 沉寂式状态栏的封装

        //沉浸式状态栏相关
        initImmersionBar();

    }

    /**
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //在Activity中实现沉浸式:java用法
        ImmersionBar
                .with(this)
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题(采用了方式一)
                .init();
    }

    /**
     * 默认不Inject
     * 使用ARouter传递参数需要重写该方法，设置返回值为true
     */
    protected boolean injectARouter() {
        return false;
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
}
