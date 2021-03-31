package cn.jack.module_fragment_02.factory;

import androidx.annotation.NonNull;

import cn.jack.module_fragment_02.mvvm.SubjectHttpRepository;
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

        if (modelClass.isAssignableFrom(SubjectHttpRepository.class)) {
            return (T) new SubjectHttpRepository();
        }

        throw new IllegalArgumentException("Unknown Repository class: " + modelClass.getName());
    }
}
