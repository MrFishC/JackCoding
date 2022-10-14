package com.jack.lib_base.uistate.loadsir.callback

import com.jack.lib_base.R
import com.kingja.loadsir.callback.Callback

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}