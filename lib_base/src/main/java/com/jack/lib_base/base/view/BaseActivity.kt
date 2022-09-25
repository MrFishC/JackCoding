package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import cn.jack.library_util.ext.closeDialog
import cn.jack.library_util.ext.loadDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.base.vm.BaseViewModle
import com.jack.lib_base.interfac.ILoadSirLisenter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_wrapper_mvvm.base.view.BaseMvvmActivity
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import finisActivity
import setStatusBarTranslucent

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:37
 * @描述
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModle>(override var block: (LayoutInflater) -> VB) :
    BaseMvvmActivity<VB, VM>(block), IStatusSwitchLisenter, ILoadSirLisenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }

        if (isRegisterLoadSir()) {
            if (!isViewRegisterLoadSir()) {
                setLoadService(this)
            }

            //监听VM层（状态布局属性的变化）
            setViewStateChangeLisenter()
        }
    }

    private fun setViewStateChangeLisenter() {
        mViewModel.changeLayoutState.observe(this){
            when(it){
                LayoutState.onLoading -> print("")
                LayoutState.onFailed -> print("")
                LayoutState.onEmpty -> print("")
                LayoutState.onTimeout -> print("")
                LayoutState.onCustom -> print("")
            }
        }
    }

    //虽然状态布局在view层变化，但是都是由数据驱动的，即m层代码 ---> vm --->v层监听vm的变化做出相应的改变
    private lateinit var mBaseLoadService: LoadService<Any>

    protected open fun setLoadService(target: Any?) {
        mBaseLoadService = LoadSir.getDefault().register(this) {
            dataReload()
        }
    }

    override fun isRegisterLoadSir(): Boolean = true

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