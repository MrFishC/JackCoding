package cn.jack.module_login;

import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.module_login.databinding.ActivitysPlashBinding;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;
import leakcanary.LeakCanary;

public class SplashActivity extends BaseSimpleActiviy<ActivitysPlashBinding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.activitys_plash;
    }

    @Override
    public void prepareData() {
        super.prepareData();
        ArouterManager.getInstance().navigation2Home();
    }
    
}