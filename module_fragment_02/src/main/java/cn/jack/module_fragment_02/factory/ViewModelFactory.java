//package cn.jack.module_fragment_02.factory;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import cn.jack.module_fragment_02.mvvm.SubjectHttpRepository;
//import cn.jack.module_fragment_02.mvvm.SubjectViewModel;
//
//
///**
// * @创建者 Jack
// * @创建时间 2021/3/17 13:56
// * @描述
// */
//public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{
//    private volatile static ViewModelFactory INSTANCE;
//    private final           Application      mApplication;
//
//    private ViewModelFactory(Application application) {
//        this.mApplication = application;
//    }
//
//    public static ViewModelFactory getInstance(Application application) {
//        if (INSTANCE == null) {
//            synchronized (ViewModelFactory.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new ViewModelFactory(application);
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//
//        if (modelClass.isAssignableFrom(SubjectViewModel.class)) {
//            return (T) new SubjectViewModel(mApplication, RepositoryFactory.getInstance().provideRepository(SubjectHttpRepository.class));
//        }
//
//        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
//
//    }
//
//}
