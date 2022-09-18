package cn.jack.module_fragment_03.simple.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import cn.jack.library_arouter.router.RouterPathFragment
import cn.jack.module_fragment_03.R
import cn.jack.module_fragment_03.databinding.FragmentHome03Binding
import cn.jack.module_fragment_03.simple.adapter.TabNavigatorAdapter
import cn.jack.module_fragment_03.simple.adapter.ViewPagerFragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.base.BaseSimpleFragment
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * 1.列表     （跳转新页面）
 *
 * 2.搜索
 * 3.导航
 * 4.公众号
 *
 * 颜色统一一下
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_THIRD)
class ModuleFragment03 : BaseSimpleFragment<FragmentHome03Binding>(FragmentHome03Binding::inflate) {

    override fun loadData() {
        super.loadData()
        initIndicator()
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(context)
        val stringArray = resources.getStringArray(R.array.square_title)
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