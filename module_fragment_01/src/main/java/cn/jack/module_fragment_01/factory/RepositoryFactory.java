//package cn.jack.module_fragment_01.factory;
//
//import androidx.annotation.NonNull;
//
//import cn.jack.module_fragment_01.mvvm.model.Repository.HomePageOneRepository;
//import jack.wrapper.base.mvvm.model.BaseModel;
//
///**
// * @创建者 Jack
// * @创建时间 2021/3/17 13:56
// * @描述
// */
//public class RepositoryFactory {
//
//    public static RepositoryFactory getInstance() {
//        return RepositoryFactoryHolder.INSTANCE;
//    }
//
//    private RepositoryFactory() {
//
//    }
//
//    private static class RepositoryFactoryHolder {
//        private static final RepositoryFactory INSTANCE = new RepositoryFactory();
//    }
//
//    public <T extends BaseModel> T provideRepository(@NonNull Class<T> modelClass) {
//
//        if (modelClass.isAssignableFrom(HomePageOneRepository.class)) {
//            return (T) new HomePageOneRepository();
//        }
//
//        throw new IllegalArgumentException("Unknown Repository class: " + modelClass.getName());
//    }
//}
