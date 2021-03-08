package cn.jack.module_fragment_02;

import com.alibaba.android.arouter.facade.annotation.Route;
import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_02.databinding.FragmentHome02Binding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

@Route(path = RouterPathFragment.HomeSecond.PAGER_HOME_SECOND)
public class ModuleFragment02 extends BaseSimpleFragment<FragmentHome02Binding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_02;
    }

}