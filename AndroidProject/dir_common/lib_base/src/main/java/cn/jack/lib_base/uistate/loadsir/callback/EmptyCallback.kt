package cn.jack.lib_base.uistate.loadsir.callback

import cn.jack.lib_base.R
import com.kingja.loadsir.callback.Callback

class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}