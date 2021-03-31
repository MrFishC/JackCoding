package cn.jack.module_login.contract;

import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import jack.wrapper.base.contract.IBaseViewStateContract;

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 15:11
 * @描述
 */
public interface IRegisterLisenter extends IBaseViewStateContract {

    void registerSuccess(UserInfo userInfo);
    void registerFailed(String msg);

}
