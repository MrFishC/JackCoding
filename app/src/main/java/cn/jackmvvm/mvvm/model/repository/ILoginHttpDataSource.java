package cn.jackmvvm.mvvm.model.repository;

import jack.wrapper.base.contract.IBaseContract;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-17
 * describe:登录页面需要执行的网络请求
 */
public interface ILoginHttpDataSource<IB extends IBaseContract.IBridge> extends IBaseContract.IHttpDataSource<IB>{

    //登录
    void login(String userName, String passwd, ILoginLisenter lisenter);

}
