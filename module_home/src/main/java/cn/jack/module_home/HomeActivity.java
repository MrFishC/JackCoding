package cn.jack.module_home;

import androidx.fragment.app.Fragment;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_home.databinding.ActivityHomeBinding;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;

/**
 * 底部导航栏参考：https://github.com/Vincent7Wong/EasyNavigation
 */
@Route(path = RouterPathActivity.Home.PAGER_HOME)
public class HomeActivity extends BaseSimpleActiviy<ActivityHomeBinding> {

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void prepareData() {
        super.prepareData();

        fragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST).navigation()));
        fragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST).navigation()));
        fragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST).navigation()));
        fragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST).navigation()));

        mBinding.navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();
    }
}