package cn.jack.library_common_business.loadsir.callback

import cn.jack.library_common_business.R
import com.kingja.loadsir.callback.Callback

/**
 * Description:TODO
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class FailedCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}