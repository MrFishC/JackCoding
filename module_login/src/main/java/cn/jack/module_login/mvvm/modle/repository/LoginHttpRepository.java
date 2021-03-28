package cn.jack.module_login.mvvm.modle.repository;

import cn.jack.module_login.api.ApiService;
import cn.jack.module_login.contract.ILoginLisenter;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ApiException;
import jack.retrofit2_rxjava2.manager.rx.RxBaseSubscriber;
import jack.retrofit2_rxjava2.manager.rx.RxFunction;
import jack.retrofit2_rxjava2.manager.rx.RxUtils;
import jack.wrapper.base.mvvm.model.BaseModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:41
 * @描述
 */
public class LoginHttpRepository extends BaseModel{

    public void login(String userName, String passwd, ILoginLisenter lisenter) {

        RxBaseSubscriber<UserInfo> userInfoRxBaseSubscriber = new RxBaseSubscriber<UserInfo>() {

            @Override
            public void onFailed(ApiException e) {
                lisenter.showToast(e.getErrorMessage());
            }

            @Override
            public void onSuccess(UserInfo data) {
                lisenter.openHomeActivity(data);
            }

        };

        addSubscribe(userInfoRxBaseSubscriber); //换一种处理内存泄露的方案

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .login(userName,passwd)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<UserInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfoRxBaseSubscriber);
    }

}
