package jack.wrapper.base.contract;

import jack.retrofit2_rxjava2.exception.ErrorStatusInfo;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-8-31
 * describe:基类契约类,子类只需要关注IBridge接口即可;
 * 子类通过实现接口IBridge,拓展自定义方法;
 *
 * m层（Model）请求数据后，会有不同的状态（成功，失败，无数据，网络异常，超时等），
 * 具体的m层通过IBaseContract的子类调自己定义的方法，从而实现让VM层对mUiStatesChange进行不同状态的设置，
 * 而v层监听了mUiStatesChange对象，则可以实现对应布局的更改；
 *
 * 注：前6种方法和吐司的方法 暂时没有放在两个接口中（IBaseContract子类也是接口，不能多实现，可以考虑将吐司接口封装在最高层）
 *
 */
public interface IBaseViewStateContract {

    //加载中
    void loading();

    //加载完成（有数据）
    void loadSuccess();

    //加载完成（数据为空）
    void loadEmpty();

    //加载失败
    void loadFailed(ErrorStatusInfo errorStatusInfo);

    //请求超时
    void timeOut();

    //网络异常
    void netError();

    //自定义
    void custom();

    //吐司
    void showToast(String toastMsg);

}
