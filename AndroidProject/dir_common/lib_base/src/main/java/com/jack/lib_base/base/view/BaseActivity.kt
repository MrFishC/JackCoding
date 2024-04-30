package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar.setStatusBarView
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
        //该方法的三个if语句执行一个即可，若执行第三个if语句，使用了fitsSystemWindows，状态栏背景设置的是透明色
        immersionBar {
            if (titBarView() != null) {
                titleBar(titBarView())
            }

            if (staBarView() != null) {
                statusBarView(staBarView())
            }

            //如果子类没有自定义实现titBarView或staBarView 则给出默认设置
            if (titBarView() == null && staBarView() == null) {
                //通用设置
                /*状态栏背景颜色，不写默认透明色*/
                statusBarColor(R.color.transparent)
                /*状态栏字体是深色，不写默认为亮色*/
                statusBarDarkFont(isDefaultStatusBar())
                /*导航栏颜色，不写默认黑色*/
                navigationBarColor(R.color.white)
                autoDarkModeEnable(true, 0.2f)
                /*自动状态栏字体和导航栏图标变色，必须指定状态栏背景颜色和导航栏背景颜色才可以自动变色哦*/
                fitsSystemWindows(true)//解决状态栏和布局重叠问题，状态栏背景只能设置纯色 fragment基类不要设置该行代码
            }
        }
    }

    /*标题栏 titBarView 方法跟 staBarView方法，是解决状态栏和布局重叠问题，任选其一[子类实现一个即可]*/
    protected open fun titBarView(): View? = null

    /*状态栏高度需要设置为0*/
    protected open fun staBarView(): View? = null

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