package cn.jack.module_login.mvvm.vm;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import javax.inject.Inject;
import cn.jack.module_login.contract.IRegisterLisenter;
import cn.jack.module_login.di.DaggerViewModelComponent;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.modle.repository.RegisterHttpRepository;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 15:00
 * @描述
 */
public class RegisterViewModel extends BaseViewModel<RegisterHttpRepository> implements IRegisterLisenter {

    @Inject
    RegisterHttpRepository mRegisterHttpRepository;

    //MutableLiveData来实现双向绑定
    public MutableLiveData<String> mPhone  = new MutableLiveData<>();
    public MutableLiveData<String> mPasswd = new MutableLiveData<>();
    public MutableLiveData<String> mPasswdAgain = new MutableLiveData<>();

    public UIChangeLiveData<UserInfo> navigation2LoginA = new UIChangeLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        DaggerViewModelComponent.builder().build().inject(this);
        mModel = mRegisterHttpRepository;
    }

    public void registerUser() {
        mModel.register(mPhone.getValue(),mPasswd.getValue(),mPasswdAgain.getValue(),this);
    }

    @Override
    public void registerSuccess(UserInfo userInfo) {
        navigation2LoginA.setValue(userInfo);
    }

    @Override
    public void registerFailed(String msg) {
        showToast(msg);
    }

}
