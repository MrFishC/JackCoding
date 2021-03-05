package cn.jackmvvm.factory;

import androidx.annotation.NonNull;

import cn.jackmvvm.mvvm.model.repository.LoginRepository;
import jack.wrapper.base.mvvm.model.BaseModel;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-17
 * describe:
 */
public class RepositoryFactory {

    /**
     *  ================================================================  单例 ================================================================
     */

    public static RepositoryFactory getInstance() {
        return RepositoryFactoryHolder.INSTANCE;
    }

    private RepositoryFactory() {

    }

    private static class RepositoryFactoryHolder {
        private static final RepositoryFactory INSTANCE = new RepositoryFactory();
    }

    /**
     *  ================================================================  单例 ================================================================
     */

    /**
     * 使用BaseModel接收XxxRepository
     * @return 具体的Repository对象
     */
    public <T extends BaseModel> T provideRepository(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LoginRepository.class)) {       //LoginRepository 类型
            return (T)new LoginRepository();
        }

        throw new IllegalArgumentException("Unknown Repository class: " + modelClass.getName());
    }

}
