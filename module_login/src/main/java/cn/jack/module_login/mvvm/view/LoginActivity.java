package cn.jack.module_login.mvvm.view;

import androidx.lifecycle.ViewModelProviders;
import cn.jack.module_login.BR;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivityLoginBinding;
import cn.jack.module_login.factory.ViewModelFactory;
import cn.jack.module_login.mvvm.vm.LoginViewModel;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 22:38
 * @描述
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

}
