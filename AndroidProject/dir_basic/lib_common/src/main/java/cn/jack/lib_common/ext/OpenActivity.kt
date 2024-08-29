package cn.jack.lib_common.ext

import androidx.fragment.app.Fragment
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.lib_base.util.DoubleCU

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 13:31
 * @描述
 */
fun Fragment.openActivityByARouter(aRouterPath: String) {
    if (!DoubleCU.isFastDoubleClick) {
        ArouterU.getInstance().navigationTo(aRouterPath)
    }
}