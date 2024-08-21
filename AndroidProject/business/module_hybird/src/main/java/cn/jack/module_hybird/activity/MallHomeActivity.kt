package cn.jack.module_hybird.activity

import cn.jack.lib_common.flutter.base.FlutterBaseActivity
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.module_hybird.MultipleModulesNameProvider

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 13:59
 * @描述
 */
@Route(path = RouterPathActivity.MallActivity.PAGER_MALL_ACTIVITY)
class MallHomeActivity : FlutterBaseActivity() {
    override val moduleName: String
        get() = MultipleModulesNameProvider.MODULE_NAME_MALL
}