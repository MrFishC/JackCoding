package cn.jack.library_common_business.loadsir.callback

import android.content.Context
import cn.jack.library_common_business.R
import android.view.View
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