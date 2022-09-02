package com.jack.lib_wrapper_mvvm.base.view

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper_mvvm.interfa.IBaseView
import com.jack.lib_wrapper_mvvm.uistate.UiStateLayout

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 18:05
 * @描述
 */
abstract class BaseMvvmActivity<VB : ViewBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperActivity<VB>(block), IBaseView {

    protected abstract val mViewModel: VM

    override fun perpareWork() {
        registorUIChangeLiveDataCallBack()
        super.perpareWork()
        observeViewModel()
    }

    private fun registorUIChangeLiveDataCallBack() {
        mViewModel.showDialogState.observe(this) {
            when (it) {
                UiStateLayout.LOADING -> println()
                else -> println()
            }
        }
    }

    open fun observeViewModel() {}

}