package com.jack.lib_wrapper.base.view

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jack.lib_wrapper.base.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper.interfa.IBaseView
import com.jack.lib_wrapper.uistate.UiStateLayout

/**
 * @创建者 Jack
 * @创建时间 2022/8/26 0026 11:03
 * @描述
 */
abstract class BaseWrapperActivity<VB : ViewBinding, VM : BaseWrapperViewModel>(val block: (LayoutInflater) -> VB) :
    AppCompatActivity(), IBaseView {

    protected val mBinding: VB by lazy { block(layoutInflater) }
    protected abstract val mViewModel: VM

    private fun init() {
        registorUIChangeLiveDataCallBack()
        prepareParam()
        prepareData()
        prepareListener()
        observeViewModel()
    }

    private fun registorUIChangeLiveDataCallBack() {
        mViewModel.showDialogState.observe(this){
            when(it){
                UiStateLayout.LOADING-> println()
                else-> println()
            }
        }
    }

    open fun observeViewModel() {}

    override fun prepareParam() {}

    override fun prepareData() {}

    override fun prepareListener() {}

}