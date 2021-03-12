package cn.jack.module_fragment_03.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.jack.library_arouter.router.RouterPathFragment;
import cn.jack.module_fragment_03.R;
import cn.jack.module_fragment_03.databinding.FragmentSquareBinding;
import jack.wrapper.base.mvvm.view.fragment.BaseSimpleFragment;

/**
 * Description: 广场
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_SQUARE)
public class SquareFragment extends BaseSimpleFragment<FragmentSquareBinding> {

    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_square;
    }

}
