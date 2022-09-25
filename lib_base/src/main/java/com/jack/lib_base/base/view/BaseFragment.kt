package com.jack.lib_base.base.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import cn.jack.library_common_business.loadsir.callback.*
import cn.jack.library_util.ext.closeDialog
import cn.jack.library_util.ext.loadDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.jack.lib_base.interfac.ILoadSirLisenter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_base.uistate.ext.postCallbackDelayed
import com.jack.lib_base.uistate.ext.postSuccessDelayed
import com.jack.lib_wrapper_mvvm.base.view.BaseMvvmFragment
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 20:07
 * @描述
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseMvvmFragment<VB, VM>(block), IStatusSwitchLisenter, ILoadSirLisenter {

    //Fragment中使用Loadsir同Activity差异较大
    private var mBaseLoadService: LoadService<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    //目前的业务需求，对指定的View设置状态布局即可
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        //在onCreateView进行register，是整个页面设置状态布局
//        if (isRegisterLoadSir()) {
//            mBaseLoadService = LoadSir.getDefault().register(mBinding.root) {
//                dataReload()
//            }
//            return mBaseLoadService.loadLayout
//        }
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    /**
     * 对Fragment中指定的View设置状态布局
     */
    protected open fun setTargetLoadService(target: Any?) {
        mBaseLoadService = LoadSir.getDefault().register(target) {
            dataReload()
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
            LayoutState.OnCustom -> mBaseLoadService?.postCallbackDelayed(CustomCallback::class.java)
            LayoutState.OnNetError -> mBaseLoadService?.postCallbackDelayed(TimeoutCallback::class.java)
            LayoutState.OnSuccess -> mBaseLoadService?.postSuccessDelayed()
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