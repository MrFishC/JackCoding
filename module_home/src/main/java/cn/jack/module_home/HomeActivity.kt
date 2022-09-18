package cn.jack.module_home

import androidx.fragment.app.Fragment
import cn.jack.library_arouter.router.RouterPathActivity
import cn.jack.library_arouter.router.RouterPathFragment
import cn.jack.module_home.databinding.ActivityHomeBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.base.BaseSimpleActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 底部导航栏参考：https://github.com/Vincent7Wong/EasyNavigation
 */
@AndroidEntryPoint
@Route(path = RouterPathActivity.Home.PAGER_HOME)
class HomeActivity : BaseSimpleActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {
    private val tabText = arrayOf("首页", "项目", "体系", "收藏")

    //未选中icon
    private val normalIcon =
        intArrayOf(R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me)

    //选中时icon
    private val selectIcon =
        intArrayOf(R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1)

    private val mFragments: MutableList<Fragment> = ArrayList()

    override fun injectARouter(): Boolean {
        return true
    }

    override fun prepareData() {
        super.prepareData()
        mFragments.add(
            ARouter.getInstance().build(RouterPathFragment.HomeFirst.PAGER_HOME_FIRST)
                .navigation() as Fragment
        )
        mFragments.add(
            ARouter.getInstance().build(RouterPathFragment.HomeSecond.PAGER_HOME_SECOND)
                .navigation() as Fragment
        )
        mFragments.add(
            ARouter.getInstance().build(RouterPathFragment.HomeThird.PAGER_HOME_THIRD)
                .navigation() as Fragment
        )
        mFragments.add(
            ARouter.getInstance().build(RouterPathFragment.HomeFour.PAGER_HOME_FOUR)
                .navigation() as Fragment
        )
        mBinding.navigationBar.titleItems(tabText)
            .normalIconItems(normalIcon)
            .selectIconItems(selectIcon)
            .fragmentList(mFragments)
            .fragmentManager(supportFragmentManager)
            .canScroll(true)
            .build()
    }
}