package cn.jack.module_login.factory;

import androidx.annotation.NonNull;

import cn.jack.module_login.mvvm.modle.repository.LoginHttpRepository;
import cn.jack.module_login.mvvm.modle.repository.RegisterHttpRepository;
import jack.wrapper.base.mvvm.model.BaseModel;

/**
 * @创建者 Jack
 * @创建时间 2021/3/17 13:56
 * @描述
 */
public class RepositoryFactory {

    public static RepositoryFactory getInstance() {
        return RepositoryFactoryHolder.INSTANCE;
    }

    private RepositoryFactory() {

    }

    private static class RepositoryFactoryHolder {
        private static final RepositoryFactory INSTANCE = new RepositoryFactory();
    }

    public <T extends BaseModel> T provideRepository(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LoginHttpRepository.class)) {
            return (T) new LoginHttpRepository();
        }else if (modelClass.isAssignableFrom(RegisterHttpRepository.class)) {
            return (T) new RegisterHttpRepository();
        }

        throw new IllegalArgumentException("Unknown Repository class: " + modelClass.getName());
    }
}
