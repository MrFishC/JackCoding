package cn.jack.module_login.mvvm.modle.repository;

import cn.jack.module_login.api.ApiService;
import cn.jack.module_login.contract.IRegisterLisenter;
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
 * @创建时间 2021/3/24 15:00
 * @描述
 */
public class RegisterHttpRepository extends BaseModel {

    public void register(String userName, String passwd, String againPasswd, IRegisterLisenter iRegisterLisenter) {

        RxBaseSubscriber<UserInfo> userInfoRxBaseSubscriber = new RxBaseSubscriber<UserInfo>() {

            @Override
            public void onFailed(ApiException e) {
                iRegisterLisenter.registerFailed(e.getErrorMessage());
            }

            @Override
            public void onSuccess(UserInfo userInfo) {
                iRegisterLisenter.registerSuccess(userInfo);
            }
        };

        addSubscribe(userInfoRxBaseSubscriber);

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .register(userName,passwd,againPasswd)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<UserInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfoRxBaseSubscriber);

    }

}
