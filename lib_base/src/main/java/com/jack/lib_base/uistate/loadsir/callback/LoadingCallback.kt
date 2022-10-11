package com.jack.lib_base.uistate.loadsir.callback

import android.content.Context
import android.view.View
import com.jack.lib_base.R
import com.kingja.loadsir.callback.Callback

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        return true
    }
}