package com.jack.lib_base.uistate.loadsir.callback

import android.content.Context
import android.view.View
import com.jack.lib_base.R
import com.kingja.loadsir.callback.Callback

/**
 * 一般情况下用不上，可以根据需求自定义实现页面内容
 */
class CustomCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_custom
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        /*可以设置吐司等提示*/
//        view.findViewById<View>(R.id.iv_gift).setOnClickListener {
//            /*可以设置吐司等提示*/
//        }
        return true
    }
}