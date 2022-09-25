package com.jack.lib_base.base.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import cn.jack.library_util.ext.closeDialog
import cn.jack.library_util.ext.loadDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_wrapper_mvvm.base.view.BaseMvvmFragment
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 20:07
 * @描述
 */
abstract class BaseFragment <VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseMvvmFragment<VB, VM>(block), IStatusSwitchLisenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    override fun visibleDialog() {
        super.visibleDialog()
        loadDialog()
    }

    override fun hideDialog() {
        super.hideDialog()
        closeDialog()
    }
}