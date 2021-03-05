package cn.jackmvvm.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import cn.jackmvvm.mvvm.model.repository.LoginRepository;
import cn.jackmvvm.mvvm.viewModel.LoginViewModel;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-17
 * describe:
 *
 * 通用的ViewModelFactory,通过指定泛型来获取对应的XxxViewModel
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private volatile static ViewModelFactory INSTANCE;
    private final Application mApplication;

    private ViewModelFactory(Application application) {
        this.mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {   //参数为XxxViewModel.class

        //new父类,然后转成子类
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, RepositoryFactory.getInstance().provideRepository(LoginRepository.class));
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());

    }

}
