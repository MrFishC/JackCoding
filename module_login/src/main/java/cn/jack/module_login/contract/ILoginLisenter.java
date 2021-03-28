package cn.jack.module_login.contract;

import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import jack.wrapper.base.contract.IBaseContract;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:39
 * @描述
 */
public interface ILoginLisenter extends IBaseContract {
    void openHomeActivity(UserInfo userInfo);
}
