package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.jack.lib_base.ext.closeDialog
import com.jack.lib_base.ext.loadDialog
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ktx.immersionBar
import com.jack.lib_base.R
import com.jack.lib_base.ext.finisActivity
import com.jack.lib_base.ext.setStatusBarTranslucent
import com.jack.lib_base.interfac.IHandler
import com.jack.lib_base.interfac.IStatusSwitchLisenter
import com.jack.lib_wrapper_mvvm.mvvm.view.BaseWrapperActivity

/**
 * @创建者 Jack
 * @创建时间 2022/8/29 0029 20:58
 * @描述
 */
abstract class BaseSimpleActivity<VB : ViewDataBinding>(override var block: (LayoutInflater) -> VB) :
    BaseWrapperActivity<VB>(block), IStatusSwitchLisenter,
    IHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isStatusBar()) {
            initImmersionBar()
        }

        if (injectARouter()) {
            ARouter.getInstance().inject(this)
        }
    }

    protected open fun initImmersionBar() {
        immersionBar {
            if (isTransparent()) {
                /*状态栏背景颜色，不写默认透明色*/
                statusBarColor(R.color.transparent)
            } else {
                statusBarColor(R.color.white)
            }
            /*状态栏字体是深色，不写默认为亮色*/
            statusBarDarkFont(isDefaultStatusBar())
            /*导航栏背景颜色，不写默认黑色*/
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