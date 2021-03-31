package jack.wrapper.base.mvvm.view.interf;

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 15:24
 * @描述 定义默认控制的状态方法
 */
public interface IStatusSwitchLisenter {

    /**
     * 默认不Inject
     * 使用ARouter传递参数需要重写该方法，设置返回值为true
     */
    default boolean injectARouter() {
        return false;
    }

    /**
     * 是否注册EventBus，默认不注册
     */
    default boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 状态栏默认为黑色
     * @return
     */
    default boolean isBlack() {
        return true;
    }

    default boolean isDefaultStatusBar() {
        return true;
    }

    /**
     * 是否使用Loadsir
     */
    default boolean isRegisterLoadSir() {
        return false;
    }

    /**
     * 指定的View是否使用Loadsir
     */
    default boolean isViewRegisterLoadSir() {
        return false;
    }

}
