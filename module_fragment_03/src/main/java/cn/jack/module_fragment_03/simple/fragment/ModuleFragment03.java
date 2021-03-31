package cn.jack.module_fragment_03.simple.fragment;

import android.view.MotionEvent;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_03.R;
import cn.jack.module_fragment_03.simple.adapter.TabNavigatorAdapter;
import cn.jack.module_fragment_03.simple.adapter.ViewPagerFragmentStateAdapter;
import cn.jack.module_fragment_03.databinding.FragmentHome03Binding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

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
public class ModuleFragment03 extends BaseSimpleFragment<FragmentHome03Binding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_03;
    }

    @Override
    public void prepareData() {
        super.prepareData();

    }

    @Override
    protected void loadData() {
        super.loadData();

        initIndicator();
    }

    private void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        String[] stringArray = getResources().getStringArray(R.array.square_title);

        TabNavigatorAdapter tabNavigatorAdapter = new TabNavigatorAdapter(Arrays.asList(stringArray));
        tabNavigatorAdapter.setOnTabClickListener((view, index) -> mBinding.viewPager2.setCurrentItem(index));
        commonNavigator.setAdapter(tabNavigatorAdapter);
        mBinding.magicIndicator.setNavigator(commonNavigator);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeThird.PAGER_HOME_SYSTEM).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(RouterPathFragment.HomeThird.PAGER_HOME_SQUARE).navigation());

        ViewPagerFragmentStateAdapter viewPagerFragmentStateAdapter = new ViewPagerFragmentStateAdapter(getActivity(),fragments);
        mBinding.viewPager2.setOffscreenPageLimit(stringArray.length);
        mBinding.viewPager2.setAdapter(viewPagerFragmentStateAdapter);

        mBinding.viewPager2.registerOnPageChangeCallback(mChangeCallback);

        mBinding.viewPager2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    private final ViewPager2.OnPageChangeCallback mChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            mBinding.magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mBinding.magicIndicator.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            mBinding.magicIndicator.onPageScrollStateChanged(state);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.viewPager2.unregisterOnPageChangeCallback(mChangeCallback);
    }

}