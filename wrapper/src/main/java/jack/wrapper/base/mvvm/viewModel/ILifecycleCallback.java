package jack.wrapper.base.mvvm.viewModel;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.NonNull;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-5-5
 * describe:
 *  1.核心：ViewModule实现了LifecycleObserver接口，实现了vm层跟v层生命周期同步；
 *
 * update:
 *  1.让BaseViewModel实现ILifecycleCallback接口。目标：vm层跟v层生命周期同步；
 *  2.封装ILifecycleCallback接口
 *      2.1.方式有二，
 *          a.继承LifecycleObserver接口（注解的方式）；
 *          b.继承DefaultLifecycleObserver接口；
 *      2.2.建议使用extends DefaultLifecycleObserver的方式
 *          2.1.1.原因：随着Java8成为主流，注解的方式会被弃用
 *          2.1.2.注意事项：
 *              a.要添加依赖 : implementation "android.arch.lifecycle:common-java8:1.1.1";
 *              b.sdk >= 24;
 *  3.目前需要知道的就是describe中的描述；
 *
 * 参考文章：
 *  1.[Android 架构组件（一）——Lifecycle](https://blog.csdn.net/zhuzp_blog/article/details/78871374),可以明白Lifecycle的使用和原理；
 *
 */
public interface ILifecycleCallback extends DefaultLifecycleObserver {

    @Override
    void onCreate(@NonNull LifecycleOwner owner);

    @Override
    void onStart(@NonNull LifecycleOwner owner);

    @Override
    void onResume(@NonNull LifecycleOwner owner);

    @Override
    void onPause(@NonNull LifecycleOwner owner);

    @Override
    void onStop(@NonNull LifecycleOwner owner);

    @Override
    void onDestroy(@NonNull LifecycleOwner owner);
}

