package com.jack.lib_base.base.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_wrapper_mvvm.base.view.BaseWrapperFragment

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 20:04
 * @描述
 */
open class BaseSimpleFragment<VB : ViewDataBinding>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperFragment<VB>(block), IStatusSwitchLisenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }
    }

}