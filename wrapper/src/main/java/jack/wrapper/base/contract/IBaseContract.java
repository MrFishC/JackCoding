package jack.wrapper.base.contract;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-8-31
 * describe:基类契约类,子类只需要关注IBridge接口即可;
 * 子类通过实现接口IBridge,拓展自定义方法;
 */
public interface IBaseContract {

    //开始加载
    void loading();

    //完成加载
    void loadFinished();

    //加载失败
    void loadFailed();

    //吐司
    void showToast(String toastMsg);

}
