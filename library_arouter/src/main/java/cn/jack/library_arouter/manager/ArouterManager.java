package cn.jack.library_arouter.manager;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_arouter.router.RouterPathFragment;

/**
 * @创建者 Jack
 * @创建时间 2021/3/4 17:07
 * @描述
 */
public class ArouterManager {

    /**
     * 使用静态内部类的方式实现单例
     */
    public static ArouterManager getInstance() {
        return ArouterManager.Holder.INSTANCE;
    }

    private static class Holder {
        private static final ArouterManager INSTANCE = new ArouterManager();
    }

    private ArouterManager() {

    }

    public void navigation2Home(){
        ARouter.getInstance().build(RouterPathActivity.Home.PAGER_HOME).navigation();
    }

    public void navigation2Login(){
        ARouter.getInstance().build(RouterPathActivity.Login.PAGER_LOGIN).navigation();
    }

    public void navigation2Register(){
        ARouter.getInstance().build(RouterPathActivity.Register.PAGER_REGISTER).navigation();
    }

    public void navigation2HomeFragment01(){
        ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST).navigation();
    }
}
