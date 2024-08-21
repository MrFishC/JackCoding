package cn.jack.lib_base.uistate.loadsir.callback

import android.content.Context
import com.jack.lib_base.R
import android.view.View
import com.kingja.loadsir.callback.Callback

class NetErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_net_error
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        /*可以设置吐司等提示*/
        return false
    }
}