package cn.jack.simple02_custom_view

import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.simple02_custom_view.databinding.ActivitySimpleCustomBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.view.BaseSimpleActivity

@Route(path = RouterPathActivity.SimpleCustom.PAGER_SIMPLE_CUSTOM)
class SimpleCustomViewActivity :
    BaseSimpleActivity<ActivitySimpleCustomBinding>(ActivitySimpleCustomBinding::inflate) {

}