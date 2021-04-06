package cn.jack.module_fragment_01.di;

import cn.jack.module_fragment_01.mvvm.vm.HomePageOneViewModle;
import dagger.Component;

/**
 * @创建者 Jack
 * @创建时间 2021/4/3 15:48
 * @描述
 */
@Component
public interface ViewModelComponent {
    void inject(HomePageOneViewModle homePageOneViewModle);
}
