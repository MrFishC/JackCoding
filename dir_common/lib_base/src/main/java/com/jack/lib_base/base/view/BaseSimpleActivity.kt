package com.jack.lib_base.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
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
//@Route(path = RouterPathActivity.SimpleRv.PAGER_SIMPLE_RV)
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