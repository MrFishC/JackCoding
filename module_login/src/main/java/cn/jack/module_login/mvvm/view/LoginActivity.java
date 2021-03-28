package cn.jack.module_login.mvvm.view;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_common_business.constant.C;
import cn.jack.library_util.SPUtils;
import cn.jack.module_login.BR;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivityLoginBinding;
import cn.jack.module_login.factory.ViewModelFactory;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.vm.LoginViewModel;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 22:38
 * @描述
 */
@Route(path = RouterPathActivity.Login.PAGER_LOGIN)
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

    @Override
    protected void registorUIChangeLiveDataCallBack() {
        super.registorUIChangeLiveDataCallBack();
        mViewModel.navigation2HomeA.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                ArouterManager.getInstance().navigation2Home();
                finish();
            }
        });
    }

    @Override
    public void prepareListener() {
        super.prepareListener();

        mBinding.registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterManager.getInstance().navigation2Register();
            }
        });

    }

    @Override
    public void prepareParam() {
        super.prepareParam();
        mViewModel.mPhone.set(SPUtils.getInstance().getData(C.C_USER_NAME,"") + "");
        mViewModel.mPasswd.set(SPUtils.getInstance().getData(C.C_USER_PASSWD,"") + "");
    }

}
