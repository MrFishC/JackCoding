package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ktx.immersionBar
import com.jack.lib_base.R
import com.jack.lib_base.ext.*
import com.jack.lib_base.interfac.IHandler
import com.jack.lib_base.interfac.ILoadSirLisenter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_base.uistate.loadsir.callback.*
import com.jack.lib_wrapper_mvvm.mvvm.view.BaseMvvmActivity
import com.jack.lib_wrapper_mvvm.mvvm.viewmodel.BaseWrapperViewModel
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:37
 * @描述
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseWrapperViewModel>(override var block: (LayoutInflater) -> VB) :
    BaseMvvmActivity<VB, VM>(block), IStatusSwitchLisenter, ILoadSirLisenter,
    IHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isStatusBar()) {
            initImmersionBar()
        }

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
                LoadingCallback::class.java, 0
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

    /**
     * 初始化沉浸式
     */
    protected open fun initImmersionBar() {
        //设置共同沉浸式样式
        immersionBar {
            if (isTransparent()) {
                /*状态栏背景颜色，不写默认透明色*/
                statusBarColor(R.color.transparent)
            } else {
                statusBarColor(R.color.white)
            }
            /*状态栏字体是深色，不写默认为亮色*/
            statusBarDarkFont(isDefaultStatusBar())
            /*导航栏颜色，不写默认黑色*/
            navigationBarColor(R.color.white)
            /*自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦*/
            autoDarkModeEnable(true, 0.2f)
            /*状态栏与布局顶部重叠解决方案，建议方案（非唯一）：子类重写initImmersionBar方法调用immersionBar的titleBar api，设置对应的控件id*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeCallbacks()
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