package cn.jack.lib_base.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ktx.immersionBar
import com.jack.lib_base.R
import com.jack.lib_base.ext.postCallbackDelayed
import com.jack.lib_base.ext.postSuccessDelayed
import com.jack.lib_base.interfac.IHandler
import com.jack.lib_base.interfac.ILoadSirLisenter
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_base.uistate.loadsir.callback.*
import com.jack.lib_base.util.DoubleCU
import com.jack.lib_wrapper_mvvm.mvvm.view.BaseWrapperFragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 20:04
 * @描述
 */
open class BaseSimpleFragment<VB : ViewDataBinding>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperFragment<VB>(block), IStatusSwitchLisenter, ILoadSirLisenter,
    IHandler {

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

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        initImmersionBar(mBinding.root)
//    }

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

    private var mBaseLoadService: LoadService<Any>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (isRegisterLoadSir()) {
            //对整个页面的布局设置状态
            mBaseLoadService = LoadSir.getDefault().register(mBinding.root) {
                dataReload()
            }
            mBaseLoadService!!.loadLayout
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }

    }

    /**
     * 对Fragment中指定的View设置状态布局
     */
    protected open fun setTargetLoadService(target: Any?) {
        mBaseLoadService = LoadSir.getDefault().register(target) {
            dataReload()
        }
    }

    //在使用的时候需要谨慎，fragment不要同时 既对整个页面的布局设置状态 又对指定的View设置状态布局
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
            LayoutState.OnNetError -> mBaseLoadService?.postCallbackDelayed(NetErrorCallback::class.java)
            LayoutState.OnSuccess -> mBaseLoadService?.postSuccessDelayed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeCallbacks()
    }

    //使用ARouter跳转页面,更改成拓展函数
//    protected open fun openActivityByARouter(aRouterPath: String) {
//        if (!DoubleCU.isFastDoubleClick) ARouter.getInstance().build(aRouterPath).navigation()
//    }

}