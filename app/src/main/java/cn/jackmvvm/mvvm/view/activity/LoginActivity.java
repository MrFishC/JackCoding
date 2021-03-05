package cn.jackmvvm.mvvm.view.activity;

import cn.jackmvvm.BR;
import cn.jackmvvm.R;
import cn.jackmvvm.databinding.ActivityLoginBinding;
import cn.jackmvvm.mvvm.viewModel.LoginViewModel;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;

/**
 * created by Jack
 * email:yucrun@163.com
 * describe:
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


    //    @Override
    //    public LoginViewModel initViewModel() {
    //        //如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
    //        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
    //        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    //    }
}
