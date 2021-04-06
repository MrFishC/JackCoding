package cn.jack.module_login.mvvm.modle.repository;

import javax.inject.Inject;

import cn.jack.library_common_business.constant.C;
import cn.jack.library_util.SPUtils;
import cn.jack.module_login.api.ApiService;
import cn.jack.module_login.contract.IRegisterLisenter;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jack.retrofit2_rxjava2.exception.ErrorStatusInfo;
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

    @Inject
    public RegisterHttpRepository() {
    }

    public void register(String userName, String passwd, String againPasswd, IRegisterLisenter iRegisterLisenter) {

        RxUtils.getInstance()
                .obtainRetrofitService(ApiService.class)
                .register(userName,passwd,againPasswd)
                .subscribeOn(Schedulers.io())
                .map(new RxFunction<UserInfo>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxBaseSubscriber<UserInfo>(this) {

                    @Override
                    public void onFailed(ErrorStatusInfo errorStatusInfo) {
                        iRegisterLisenter.registerFailed(errorStatusInfo.message);
                    }

                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        SPUtils.getInstance().putData(C.C_USER_NAME,userName);
                        SPUtils.getInstance().putData(C.C_USER_PASSWD,passwd);
                        iRegisterLisenter.registerSuccess(userInfo);
                    }
                });

    }

}
