package com.jack.lib_wrapper_mvvm.base.view

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.jack.lib_wrapper_mvvm.interfa.IBaseView
import com.jack.lib_wrapper_mvvm.uistate.DialogState

/**
 * @创建者 Jack
 * @创建时间 2022/8/27 0027 18:05
 * @描述
 */
abstract class BaseMvvmActivity<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperActivity<VB>(block), IBaseView {

    protected abstract val mViewModel: VM

    override fun perpareWork() {
        initViewModel()
        super.perpareWork()
        observeViewModel()
    }

    private fun initViewModel() {
//        mBinding.setVariable(initVariableId(), mViewModel);//使用kotlin代码后，直接在具体子类调用mBinding.setX方法更方便
        //让ViewModel拥有View的生命周期感应:即mViewModel成为观察者
        lifecycle.addObserver(mViewModel);

        mViewModel.showDialogState.observe(this) {
            when (it) {
                DialogState.LOADING -> visibleDialog()
                else -> hideDialog()
            }
        }
    }

    open fun observeViewModel() {}

}