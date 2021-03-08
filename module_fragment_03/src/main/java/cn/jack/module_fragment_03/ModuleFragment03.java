package cn.jack.module_fragment_03;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_03.databinding.FragmentHome03Binding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_THIRD)
public class ModuleFragment03 extends BaseSimpleFragment<FragmentHome03Binding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_03;
    }

}