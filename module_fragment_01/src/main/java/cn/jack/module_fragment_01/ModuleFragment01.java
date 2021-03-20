package cn.jack.module_fragment_01;

import com.alibaba.android.arouter.facade.annotation.Route;
import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_01.databinding.FragmentHome01Binding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

/**
 * 1.列表
 * 2.轮播图
 * 3.搜索
 */
@Route(path = RouterPathFragment.HomeFirst.PAGER_HOME_FIRST)
public class ModuleFragment01 extends BaseSimpleFragment<FragmentHome01Binding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_01;
    }

}