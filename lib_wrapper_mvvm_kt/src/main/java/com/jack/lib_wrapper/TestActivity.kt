package com.jack.lib_wrapper

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.jack.lib_wrapper.base.view.BaseWrapperActivity
import com.jack.lib_wrapper.uistate.UiStateLayout
import fake.`package`.name.`for`.sync.databinding.ActivityLoginBinding

/**
 * @创建者 Jack
 * @创建时间 2022/8/26 0026 11:40
 * @描述
 */
class TestActivity :
    BaseWrapperActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::inflate) {

    override val mViewModel: LoginViewModel by viewModels()
}