package jack.wrapper.base.contract.base;

/**
 * 查询网络数据
 * 或者
 * 查询本地数据
 *
 */
public interface IBaseBridge<T> extends IContract.IBridge {

    //加载成功
    void success(T t);

    //加载失败
    void failed(T t);

    //没有数据
    void dataEmpty(T t);

}
