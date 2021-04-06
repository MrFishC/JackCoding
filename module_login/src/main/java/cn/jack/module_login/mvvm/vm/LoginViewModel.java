package cn.jack.module_login.mvvm.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import javax.inject.Inject;

import cn.jack.module_login.contract.ILoginLisenter;
import cn.jack.module_login.di.DaggerViewModelComponent;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.modle.repository.LoginHttpRepository;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:45
 * @描述
 */
public class LoginViewModel extends BaseViewModel<LoginHttpRepository> implements ILoginLisenter {

    @Inject
    LoginHttpRepository mLoginHttpRepository;

    //帐号
    public ObservableField<String> mPhone = new ObservableField<>();
    //密码
    public ObservableField<String> mPasswd   = new ObservableField<>();

    public UIChangeLiveData<UserInfo> navigation2HomeA = new UIChangeLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        DaggerViewModelComponent.builder().build().inject(this);
        mModel = mLoginHttpRepository;
    }

    public void userLogin(){
        mModel.login(mPhone.get(),mPasswd.get(),LoginViewModel.this);
    }

    @Override
    public void openHomeActivity(UserInfo userInfo) {
        navigation2HomeA.setValue(userInfo);
    }

}
