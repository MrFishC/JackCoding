package com.jack.lib_wrapper_mvvm.base.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jack.lib_wrapper_mvvm.base.model.BaseWrapperModel
import com.jack.lib_wrapper_mvvm.uistate.DialogState

/**
 * @创建者 Jack
 * @创建时间 2022/8/25 0025 20:48
 * @描述
 */

abstract class BaseWrapperViewModel constructor() : ViewModel() , DefaultLifecycleObserver {
    /*这里的Livedata用于Vm层管理View层显示或关闭对话框(不涉及任何网络请求相关的,网络请求部分会单独封装)*/
    val showDialogState by lazy { MutableLiveData<DialogState>() }

    private var mModel: BaseWrapperModel? = null

    //一般情况下，自定义的子类调用BaseWrapperViewModel的无参构造方法即可，保留有参是为了使用Rxjava的时候，处理内存泄露的问题
    constructor(model: BaseWrapperModel) : this(){
        mModel = model
    }

    //VM层驱动V层UI的更新--->显示与隐藏对话框
    //在使用了Flow + retrofit的情况下，UI的更新可以统一在View层设置，很方便，不需要VM通过数据去更新V层UI，如果不使用Flow的情况（假设使用Rxjava），该方法可以保留
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
            hide -> showDialogState.postValue(DialogState.OnHide)
            loading -> showDialogState.postValue(DialogState.OnLoading)
        }
    }

    override fun onCleared() {
        super.onCleared()
        /*viewmodle层执行onCleared方法时,执行BaseModel的onCleared方法*/
        mModel?.onCleared()
    }

}