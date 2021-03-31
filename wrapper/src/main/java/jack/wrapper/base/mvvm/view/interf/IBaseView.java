package jack.wrapper.base.mvvm.view.interf;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-27
 * describe:
 *   1.由BaseActivity实现该接口,该接口中定义的方法由BaseActivity的子类去实现;
 *   2.当然可以直接将该类的方法定义在BaseActivity中,但是定义在一个单独的接口中,结构会更好,结构清晰的提升会增加系统的复杂度;
 */

public interface IBaseView {

    /**
     * 初始化参数
     */
    void prepareParam();

    /**
     * 初始化数据
     */
    void prepareData();

    /**
     * 初始化监听
     */
    void prepareListener();

}
