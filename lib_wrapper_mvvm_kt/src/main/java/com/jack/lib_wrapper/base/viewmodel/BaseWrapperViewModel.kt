package com.jack.lib_wrapper.base.viewmodel

import BaseWrapperModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jack.lib_wrapper.uistate.UiStateLayout

/**
 * @创建者 Jack
 * @创建时间 2022/8/25 0025 20:48
 * @描述
 */

abstract class BaseWrapperViewModel constructor() : ViewModel() {
    /*这里的Livedata用于Vm层管理View层显示或关闭对话框(不涉及任何网络请求相关的,网络请求部分会单独封装)*/
    val showDialogState by lazy { MutableLiveData<UiStateLayout>() }

    private lateinit var mModel: BaseWrapperModel

    constructor(model: BaseWrapperModel) : this(){
        mModel = model
    }

    protected fun isShowDialog(
        hide: Boolean = false,
        loading: Boolean = false
    ) {
        // 对参数进行校验
        var count = 0
        if (hide) count++
        if (loading) count++
        when {
            count == 0 -> throw IllegalArgumentException("count must be equal 1,not 0")
            count > 1 -> throw IllegalArgumentException("count must be equal 1,not > 1")
        }

        // 修改状态
        when {
            hide -> showDialogState.postValue(UiStateLayout.HIDE)
            loading -> showDialogState.postValue(UiStateLayout.LOADING)
        }
    }

    override fun onCleared() {
        super.onCleared()
        /*viewmodle层执行onCleared方法时,执行BaseModel的onCleared方法*/
        mModel.onCleared()
    }

}