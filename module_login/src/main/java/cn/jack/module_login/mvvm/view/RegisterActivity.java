package cn.jack.module_login.mvvm.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.module_login.BR;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivityRegisterBinding;
import cn.jack.module_login.factory.ViewModelFactory;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.vm.RegisterViewModel;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;

/**
 * @创建者 Jack
 * @创建时间 2021/3/22 16:57
 * @描述
 *
 */
@Route(path = RouterPathActivity.Register.PAGER_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {

    @Override
    public int initContentView() {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RegisterViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RegisterViewModel.class);
    }

    @Override
    protected void registorUIChangeLiveDataCallBack() {
        super.registorUIChangeLiveDataCallBack();

        mViewModel.navigation2LoginA.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                //登录页面展示新注册的账号和密码
                openActivity(LoginActivity.class);
                finish();
            }
        });

    }


}
