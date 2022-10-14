package com.jack.lib_base.base.vm

import com.jack.lib_wrapper_mvvm.mvvm.model.BaseWrapperModel
import com.jack.lib_wrapper_mvvm.mvvm.viewmodel.BaseWrapperViewModel

/**
 * @创建者 Jack
 * @描述
 */
open class BaseViewModle : BaseWrapperViewModel {

    constructor() : super() {

    }

    constructor(model: BaseWrapperModel) : super(model) {

    }

}