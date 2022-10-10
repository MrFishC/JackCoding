package com.jack.lib_wrapper_mvvm.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jack.lib_wrapper_mvvm.interfa.IBaseView

/**
 * @创建者 Jack
 * @创建时间 2022/9/15 19:51
 * @描述
 */
abstract class BaseWrapperFragment<VB : ViewDataBinding>(open var block: (LayoutInflater) -> VB) :
    Fragment(), IBaseView {

    protected val mBinding: VB by lazy { block(layoutInflater) }

    //Fragment的View加载完毕的标记
    private var mIsViewCreated = false

    //Fragment对用户可见的标记
    private var mIsUIVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prepareParam()
        super.onViewCreated(view, savedInstanceState)
        perpareWork()

        mIsViewCreated = true
        lazyLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsViewCreated = false
        mIsUIVisible = false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            mIsUIVisible = true
            lazyLoad()
        } else {
            mIsUIVisible = false
        }
    }

    private fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (mIsViewCreated && mIsUIVisible) {
            loadData()
            //数据加载完毕,恢复标记,防止重复加载
            mIsViewCreated = false
            mIsUIVisible = false
        }
    }

    open fun perpareWork() {
        prepareData()
        prepareListener()
    }

    //需要懒加载的子类则自行重写该方法
    open fun loadData() {
    }

    override fun prepareParam() {

    }

    override fun prepareData() {

    }

    override fun prepareListener() {

    }

    open fun hideDialog() {

    }

    open fun visibleDialog() {

    }
}