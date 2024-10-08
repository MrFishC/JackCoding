package cn.jack.lib_wrapper_mvvm.mvvm.view

import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.jack.lib_wrapper_mvvm.mvvm.viewmodel.BaseWrapperViewModel
import cn.jack.lib_wrapper_mvvm.uistate.DialogState

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 20:01
 * @描述
 */
abstract class BaseMvvmFragment<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperFragment<VB>(block) {

    protected abstract val mViewModel: VM

    override fun perpareWork() {
        initViewModel()
        super.perpareWork()
        observeViewModel()
    }

    private fun initViewModel() {
        lifecycle.addObserver(mViewModel);

        mViewModel.showDialogState.observe(this) {
            when (it) {
                DialogState.OnLoading -> visibleDialog()
                else -> hideDialog()
            }
        }
    }

    open fun observeViewModel() {}
}