package cn.jack.module_login.mvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProviders;
import com.alibaba.android.arouter.facade.annotation.Route;
import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.library_arouter.router.RouterPathActivity;
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

        System.out.println("跳转... " + System.currentTimeMillis());

        Intent intent = getIntent();

        System.out.println("intent " + (intent ==null));

        if(intent != null){
            Bundle bundle = intent.getExtras();
            System.out.println("bundle " + (bundle ==null));
            if(bundle != null){
                String phone = bundle.getString("phone");
                String passwd = bundle.getString("passwd");
                mViewModel.mPhone.set(phone);
                mViewModel.mPasswd.set(passwd);

                System.out.println("phone " + phone + " passwd " + passwd);
            }
        }

    }
}
