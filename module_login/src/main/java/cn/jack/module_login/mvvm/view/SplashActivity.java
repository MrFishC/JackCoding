package cn.jack.module_login.mvvm.view;

import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivitysPlashBinding;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;

public class SplashActivity extends BaseSimpleActiviy<ActivitysPlashBinding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.activitys_plash;
    }

    @Override
    public void prepareData() {
        super.prepareData();
        ArouterManager.getInstance().navigation2Login();
        finish();
    }
    
}