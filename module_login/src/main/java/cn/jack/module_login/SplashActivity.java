package cn.jack.module_login;

import cn.jack.library_arouter.manager.ArouterManager;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;

public class SplashActivity extends BaseSimpleActiviy {

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