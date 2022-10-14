package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.jack.lib_base.ext.closeDialog
import com.jack.lib_base.ext.loadDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.ext.finisActivity
import com.jack.lib_base.ext.setStatusBarTranslucent
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_wrapper_mvvm.mvvm.view.BaseWrapperActivity

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:58
 * @描述
 */
abstract class BaseSimpleActivity<VB : ViewDataBinding>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperActivity<VB>(block), IStatusSwitchLisenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    override fun prepareParam() {
        setStatusBarTranslucent(Color.BLACK)
    }

    override fun visibleDialog() {
        super.visibleDialog()
        loadDialog()
    }

    override fun onBackPressed() {
        finisActivity()
    }

    override fun hideDialog() {
        super.hideDialog()
        closeDialog()
    }
}