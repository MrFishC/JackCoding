package cn.jack.module_fragment_04;

import com.alibaba.android.arouter.facade.annotation.Route;
import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_04.databinding.FragmentHome04Binding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

@Route(path = RouterPathFragment.HomeFour.PAGER_HOME_FOUR)
public class ModuleFragment04 extends BaseSimpleFragment<FragmentHome04Binding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_home_04;
    }

}