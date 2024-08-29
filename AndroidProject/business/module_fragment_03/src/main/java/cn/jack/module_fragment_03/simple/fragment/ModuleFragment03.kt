package cn.jack.module_fragment_03.simple.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.module_fragment_03.R
import cn.jack.module_fragment_03.databinding.ModuleFragment03FragmentHome03Binding
import cn.jack.module_fragment_03.simple.adapter.TabNavigatorAdapter
import cn.jack.module_fragment_03.simple.adapter.ViewPagerFragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import cn.jack.lib_base.base.view.BaseSimpleFragment
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * 1.列表     （跳转新页面）
 *
 * 2.搜索
 * 3.导航
 * 4.公众号
 *
 * 颜色统一一下
 *
 * 存在的问题，在两个子fragment，使用对指定的View设置状态布局 没有效果。 目前按照对整个页面设置状态布局的方式。
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_THIRD)
class ModuleFragment03 : BaseSimpleFragment<ModuleFragment03FragmentHome03Binding>(ModuleFragment03FragmentHome03Binding::inflate) {

    override fun staBarView(view: View): View = mBinding.defaultIncludeStaBar.statusBar

    override fun loadData() {
        super.loadData()
        initIndicator()
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(context)
        val stringArray = resources.getStringArray(R.array.module_fragment_03_square_title)
        val tabNavigatorAdapter = TabNavigatorAdapter(listOf(*stringArray))             //未明白这里代码含义
        tabNavigatorAdapter.setOnTabClickListener { _, index: Int ->
            mBinding.viewPager2.currentItem = index
        }
        commonNavigator.adapter = tabNavigatorAdapter
        mBinding.magicIndicator.navigator = commonNavigator
        val fragments: MutableList<Fragment> = ArrayList()
        fragments.add(
            ARouter.getInstance().build(RouterPathFragment.HomeThird.PAGER_HOME_SYSTEM)
                .navigation() as Fragment
        )
        fragments.add(
            ARouter.getInstance().build(RouterPathFragment.HomeThird.PAGER_HOME_SQUARE)
                .navigation() as Fragment
        )

        val viewPagerFragmentStateAdapter = ViewPagerFragmentStateAdapter(
            requireActivity(), fragments
        )

        //ViewPager2出来之后 FragmentStatePagerAdapter 就退出历史舞台。
        mBinding.viewPager2.offscreenPageLimit = stringArray.size
        mBinding.viewPager2.adapter = viewPagerFragmentStateAdapter
        mBinding.viewPager2.registerOnPageChangeCallback(mChangeCallback)
    }

    private val mChangeCallback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            mBinding.magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mBinding.magicIndicator.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            mBinding.magicIndicator.onPageScrollStateChanged(state)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.viewPager2.unregisterOnPageChangeCallback(mChangeCallback)
    }
}