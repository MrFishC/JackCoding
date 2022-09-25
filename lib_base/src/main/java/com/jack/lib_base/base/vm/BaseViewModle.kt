package com.jack.lib_base.base.vm

import androidx.lifecycle.MutableLiveData
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_wrapper_mvvm.base.model.BaseWrapperModel
import com.jack.lib_wrapper_mvvm.base.viewmodel.BaseWrapperViewModel

/**
 * @创建者 Jack
 * @描述
 */
open class BaseViewModle : BaseWrapperViewModel {
    val changeLayoutState by lazy { MutableLiveData<LayoutState<LayoutState.onLoading>>() }

    constructor() : super() {

    }

    constructor(model: BaseWrapperModel) : super(model) {

    }


}