package jack.wrapper.base.mvvm.model;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:服务于Repository,单独抽成接口
 */
public interface IModel {
    /**
     * View层销毁时,ViewModel销毁,对应Model销毁,对应Repository中,使用rxjava执行联网请求的时候防止内存泄露问题;
     */
    void onCleared();
}
