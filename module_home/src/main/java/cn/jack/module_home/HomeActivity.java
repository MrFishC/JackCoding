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

    private String[] tabText = {"首页", "项目", "体系", "收藏"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    public void prepareData() {
        super.prepareData();

        mFragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST).navigation()));
        mFragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeSecond.PAGER_HOME_SECOND).navigation()));
        mFragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeThird.PAGER_HOME_THIRD).navigation()));
        mFragments.add(((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeFour.PAGER_HOME_FOUR).navigation()));

        mBinding.navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(mFragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();


    }

}