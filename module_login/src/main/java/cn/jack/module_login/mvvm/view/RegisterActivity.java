package cn.jack.module_login.mvvm.view;

import android.content.Intent;
import android.os.Bundle;

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
import jack.wrapper.bus.Event;

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
                //关闭当前页面
                //打开登录页面
                //登录页面展示新注册的账号和密码

                //todo 封装activity的跳转
                Bundle bundle = new Bundle();
//                bundle.putString("phone",userInfo.getUsername());
//                bundle.putString("passwd",userInfo.getPassword());

                bundle.putString("phone","18811116666");
                bundle.putString("passwd","123456");

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

}
