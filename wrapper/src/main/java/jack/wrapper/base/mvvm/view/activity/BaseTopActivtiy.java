package jack.wrapper.base.mvvm.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import org.greenrobot.eventbus.Subscribe;
import jack.wrapper.R;
import jack.wrapper.base.mvvm.view.IBaseView;
import jack.wrapper.bus.Event;
import jack.wrapper.bus.EventBusUtil;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-14
 * describe:BaseActivity和BaseStateActivity的父类,去掉public,只能由BaseActivity和BaseStateActivity继承
 *
 * update：更改为public
 */
public abstract class BaseTopActivtiy extends AppCompatActivity implements IBaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (injectARouter()) {
            ARouter.getInstance().inject(this);
        }

        //事件总线
        if (isRegisterEventBus()) {
            EventBusUtil.getInstance().register(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.getInstance().unregister(this);
        }
    }

    @Subscribe
    public <T> void onEventBusCome(Event<T> event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(sticky = true)
    public <T> void onStickyEventBusCome(Event<T> event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接收到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    /**
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        /**
         * 1.若我们采用convertView = inflater.inflate(R.layout.item_list, null);方式填充视图，item布局中的根视图的layout_XX属性会被忽略掉，然后设置成默认的包裹内容方式
         * 2.如果我们想保证item的视图中的参数不被改变，我们需要使用convertView = inflater.inflate(R.layout.item_list, parent,false);这种方式进行视图的填充
         * 3.除了使用这种方式，我们还可以设置item布局的根视图为包裹内容，然后设置内部控件的高度等属性，这样就不会修改显示方式了。
         * ————————————————
         * 原文链接：https://blog.csdn.net/dangnianmingyue_gg/article/details/71420114?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.control
         */
//        View view = LayoutInflater.from(this).inflate(R.layout.statusview,null,false);
        //在Activity中实现沉浸式:java用法

        if(isDefaultStatusBar()){
            if(isBlack()){
                ImmersionBar
                        .with(this)
                        .statusBarView(R.id.status_bar_view)      //解决状态栏和布局重叠问题
                        .statusBarDarkFont(true)
                        .init();
            }else {
                ImmersionBar
                        .with(this)
                        .statusBarView(R.id.status_bar_view)
                        .init();
            }
        }else {
            //不使用默认的 view填充状态栏，会使用 （方案一）作为约束
            //状态栏与布局顶部重叠解决方案，六种方案根据不同需求任选其一
            //https://github.com/gyf-dev/ImmersionBar#%E7%8A%B6%E6%80%81%E6%A0%8F%E4%B8%8E%E5%B8%83%E5%B1%80%E9%A1%B6%E9%83%A8%E9%87%8D%E5%8F%A0%E8%A7%A3%E5%86%B3%E6%96%B9%E6%A1%88%E5%85%AD%E7%A7%8D%E6%96%B9%E6%A1%88%E6%A0%B9%E6%8D%AE%E4%B8%8D%E5%90%8C%E9%9C%80%E6%B1%82%E4%BB%BB%E9%80%89%E5%85%B6%E4%B8%80
            if(isBlack()){
                ImmersionBar
                        .with(this)
                        .statusBarDarkFont(true)
                        .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题(采用了方式一需要设置)
                        .init();
            }else {
                ImmersionBar
                        .with(this)
                        .keyboardEnable(true)
                        .init();
            }
        }

    }

    /**
     * 状态栏默认为黑色
     * @return
     */
    protected boolean isBlack() {
        return true;
    }

    protected boolean isDefaultStatusBar() {
        return true;
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

    /**
     * 是否注册EventBus，默认不注册
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

}
