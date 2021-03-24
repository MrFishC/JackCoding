package cn.jack.module_login.mvvm.vm;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import cn.jack.module_login.contract.IRegisterLisenter;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.modle.repository.RegisterHttpRepository;
import cn.jack.module_login.mvvm.view.LoginActivity;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;
import jack.wrapper.base.mvvm.viewModel.liveData.UIChangeLiveData;

/**
 * @创建者 Jack
 * @创建时间 2021/3/24 15:00
 * @描述
 */
public class RegisterViewModel extends BaseViewModel<RegisterHttpRepository> implements IRegisterLisenter {

    //MutableLiveData来实现双向绑定
    public MutableLiveData<String> mPhone  = new MutableLiveData<>();
    public MutableLiveData<String> mPasswd = new MutableLiveData<>();
    public MutableLiveData<String> mPasswdAgain = new MutableLiveData<>();

    public UIChangeLiveData<UserInfo> navigation2LoginA = new UIChangeLiveData<>();

    public RegisterViewModel(@NonNull Application application, RegisterHttpRepository model) {
        super(application, model);
    }

    public View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registerUser();
        }
    };

    private void registerUser() {
        String userName = mPhone.getValue();
        String passwd = mPasswd.getValue();
        String againPasswd = mPasswdAgain.getValue();

        System.out.println("userName " + userName);
        System.out.println("passwd " + passwd);
        System.out.println("againPasswd " + againPasswd);

        if(TextUtils.isEmpty(userName)){
            showToast("请再次输入用户名");
            return;
        }

        if(TextUtils.isEmpty(passwd)){
            showToast("请输入密码");
            return;
        }

        if(TextUtils.isEmpty(againPasswd)){
            showToast("请再次输入密码");
            return;
        }

        mModel.register(userName,passwd,againPasswd,this);
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
