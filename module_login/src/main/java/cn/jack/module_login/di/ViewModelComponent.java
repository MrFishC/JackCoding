package cn.jack.module_login.di;

import cn.jack.module_login.mvvm.vm.LoginViewModel;
import cn.jack.module_login.mvvm.vm.RegisterViewModel;
import dagger.Component;

/**
 * @创建者 Jack
 * @创建时间 2021/4/3 15:48
 * @描述
 */
@Component
public interface ViewModelComponent {
    void inject(LoginViewModel loginViewModel);
    void inject(RegisterViewModel registerViewModel);
}
