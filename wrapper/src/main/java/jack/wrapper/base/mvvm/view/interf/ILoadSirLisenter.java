package jack.wrapper.base.mvvm.view.interf;

/**
 * @创建者 Jack
 * @创建时间 2021/3/30 15:43
 * @描述
 */
public interface ILoadSirLisenter {
    //抽取到接口中：fragment和activity都可以使用，复用性更高
    //使用loadsir需要重写该方法
    default void dataReload(){

    }
}
