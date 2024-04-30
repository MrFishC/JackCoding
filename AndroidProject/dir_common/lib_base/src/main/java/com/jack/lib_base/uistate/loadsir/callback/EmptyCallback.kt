package com.jack.lib_base.uistate.loadsir.callback

import com.jack.lib_base.R
import com.kingja.loadsir.callback.Callback

class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}