package com.jack.lib_base.uistate.loadsir.callback

import android.content.Context
import android.view.View
import android.widget.Toast
import com.jack.lib_base.R
import com.kingja.loadsir.callback.Callback

class TimeoutCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_timeout
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        /*可以设置吐司等提示*/
        return false
    }
}