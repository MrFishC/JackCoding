package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.jack.lib_base.uistate.loadsir.callback.*
import cn.jack.library_util.ext.closeDialog
import cn.jack.library_util.ext.loadDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.interfac.ILoadSirLisenter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_base.ext.postCallbackDelayed
import com.jack.lib_base.ext.postSuccessDelayed
import com.jack.lib_wrapper_mvvm.base.view.BaseMvvmActivity
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import finisActivity
import setStatusBarTranslucent

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:37
 * @描述
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseMvvmActivity<VB, VM>(block), IStatusSwitchLisenter, ILoadSirLisenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }

        if (isRegisterLoadSir()) {
            setTargetLoadService(this)
        }

    }

    protected fun setLayoutState(layoutState: LayoutState) {
        when (layoutState) {
            LayoutState.OnLoading -> mBaseLoadService?.postCallbackDelayed(
                LoadingCallback::class.java,
                0
            )
            LayoutState.OnFailed -> mBaseLoadService?.postCallbackDelayed(FailedCallback::class.java)
            LayoutState.OnEmpty -> mBaseLoadService?.postCallbackDelayed(EmptyCallback::class.java)
            LayoutState.OnTimeout -> mBaseLoadService?.postCallbackDelayed(TimeoutCallback::class.java)
            LayoutState.OnNetError -> mBaseLoadService?.postCallbackDelayed(NetErrorCallback::class.java)
            LayoutState.OnCustom -> mBaseLoadService?.postCallbackDelayed(CustomCallback::class.java)
            LayoutState.OnSuccess -> mBaseLoadService?.postSuccessDelayed()
        }
    }

    private var mBaseLoadService: LoadService<Any>? = null

    /*指定的View若要设置，则需要主动调用该方法。注意：建议同一个页面，isRegisterLoadSir设置为true和主动调用下方方法，根据业务两者取其一即可，避免同时使用*/
    protected open fun setTargetLoadService(target: Any?) {
        mBaseLoadService = LoadSir.getDefault().register(target) {
            dataReload()
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