package cn.jack.module_fragment_02.di;

import cn.jack.module_fragment_02.mvvm.SubjectViewModel;
import dagger.Component;

/**
 * @创建者 Jack
 * @创建时间 2021/4/4 0:08
 * @描述
 */
@Component
public interface ViewModelComponent {
   void inject(SubjectViewModel subjectViewModel);
}
