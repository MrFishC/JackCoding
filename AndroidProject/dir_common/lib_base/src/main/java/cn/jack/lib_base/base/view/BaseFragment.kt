package cn.jack.lib_base.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ktx.immersionBar
import com.jack.lib_base.R
import com.jack.lib_base.ext.closeDialog
import com.jack.lib_base.ext.loadDialog
import com.jack.lib_base.ext.postCallbackDelayed
import com.jack.lib_base.ext.postSuccessDelayed
import com.jack.lib_base.interfac.IHandler
import com.jack.lib_base.interfac.ILoadSirLisenter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_base.uistate.loadsir.callback.*
import com.jack.lib_wrapper_mvvm.mvvm.view.BaseMvvmFragment
import com.jack.lib_wrapper_mvvm.mvvm.viewmodel.BaseWrapperViewModel
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 20:07
 * @描述
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseMvvmFragment<VB, VM>(block), IStatusSwitchLisenter, ILoadSirLisenter,
    IHandler {

    //Fragment中使用Loadsir同Activity差异较大
    private var mBaseLoadService: LoadService<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isStatusBar()) {
            initImmersionBar(view)
        }
    }

    protected open fun initImmersionBar(view: View) {
        immersionBar {
            if (titBarView(view) != null) {
                titleBar(titBarView(view))
            }

            if (staBarView(view) != null) {
                statusBarView(staBarView(view))
            }

            //通用设置
            statusBarColor(R.color.transparent)
            statusBarDarkFont(isDefaultStatusBar())
            navigationBarColor(R.color.white)
            autoDarkModeEnable(true, 0.2f)
        }
    }

    /*标题栏 titBarView 方法跟 staBarView方法，是解决状态栏和布局重叠问题，任选其一[子类实现一个即可]*/
    protected open fun titBarView(view: View): View? = null

    /*状态栏高度需要设置为0*/
    protected open fun staBarView(view: View): View? = null

    //目前的业务需求，默认是对指定的View设置状态布局  BaseSimpleFragment中封装的是对整个页面设置状态布局
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
            LayoutState.OnNetError -> mBaseLoadService?.postCallbackDelayed(NetErrorCallback::class.java)
            LayoutState.OnCustom -> mBaseLoadService?.postCallbackDelayed(CustomCallback::class.java)
            LayoutState.OnSuccess -> mBaseLoadService?.postSuccessDelayed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeCallbacks()
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