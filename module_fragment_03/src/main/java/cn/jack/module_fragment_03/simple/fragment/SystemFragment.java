package cn.jack.module_fragment_03.simple.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_03.R;
import cn.jack.module_fragment_03.databinding.FragmentSystemBinding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

/**
 * Description: 体系
 *
 * 使用随机布局实现
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_SYSTEM)
public class SystemFragment extends BaseSimpleFragment<FragmentSystemBinding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_system;
    }

}
