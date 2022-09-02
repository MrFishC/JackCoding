package com.jack.lib_wrapper_mvvm.base.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jack.lib_wrapper_mvvm.ext.finisActivity
import com.jack.lib_wrapper_mvvm.ext.setStatusBarTranslucent
import com.jack.lib_wrapper_mvvm.interfa.IBaseView

/**
 * @创建者 Jack
 * @创建时间 2022/8/26 0026 11:03
 * @描述
 */
abstract class BaseWrapperActivity<VB : ViewBinding>(open var block: (LayoutInflater) -> VB) :
    AppCompatActivity(), IBaseView {

    protected val mBinding: VB by lazy { block(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        prepareParam()
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        perpareWork()
    }

    protected open fun perpareWork() {
        prepareData()
        prepareListener()
    }

    override fun prepareParam() {
        setStatusBarTranslucent(Color.BLACK)
    }

    override fun prepareData() {}

    override fun prepareListener() {}

    override fun onBackPressed() {
        finisActivity()
    }
}