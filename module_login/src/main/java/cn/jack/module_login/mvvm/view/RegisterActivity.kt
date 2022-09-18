package cn.jack.module_login.mvvm.view

import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.manager.ArouterManager
import cn.jack.library_arouter.router.RouterPathActivity
import cn.jack.library_common_business.constant.C
import cn.jack.library_util.KvStoreUtil
import cn.jack.library_util.ToastU
import cn.jack.library_util.ext.showToast
import cn.jack.module_login.databinding.ActivityRegisterBinding
import cn.jack.module_login.mvvm.modle.entity.InfoVerification
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.vm.RegisterViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.BaseActivity
import com.jack.lib_wrapper_net.model.EventResult
import com.jakewharton.rxbinding3.widget.textChanges
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import kotlinx.coroutines.launch

/**
 * @创建者 Jack
 * @创建时间 2021/3/22 16:57
 * @描述
 */
@AndroidEntryPoint
@Route(path = RouterPathActivity.Register.PAGER_REGISTER)
class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterViewModel>(ActivityRegisterBinding::inflate) {
    override val mViewModel: RegisterViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.registerUserInfo.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> registerSuccess(it.data)
                        is EventResult.OnError -> {
                            hideDialog()
                            showToast(it.throwable.message)
                        }
                        is EventResult.OnComplete -> hideDialog()
                    }
                }
            }
        }
    }

    private fun registerSuccess(data: UserInfo?) {
        KvStoreUtil.getInstance()?.save(C.Login.user_name, data?.username)
        ArouterManager.getInstance().navigation2Login()
    }

    override fun prepareListener() {
        super.prepareListener()

        mBinding.registerInfo.setOnClickListener {
            mViewModel.registerUser(
                mBinding.etAccount.text.toString(),
                mBinding.etPassword.text.toString(),
                mBinding.againPassword.text.toString()
            )
        }

        Observable.combineLatest(
            mBinding.etAccount.textChanges(),
            mBinding.etPassword.textChanges(),
            mBinding.againPassword.textChanges()
        ) { phone, passwd, passwdAgain -> isValidInfo(phone, passwd, passwdAgain) }
            .autoDispose(AndroidLifecycleScopeProvider.from(this))
            .subscribe { it -> mBinding.registerInfo.isEnabled = it.flag }
    }

    private fun isValidInfo(
        phone: CharSequence,
        passwd: CharSequence,
        passwdAgain: CharSequence
    ): InfoVerification {
        val registerInfoVerification = InfoVerification()
        if (phone.isEmpty()) {
            registerInfoVerification.flag = false
            registerInfoVerification.message = "手机号码不能为空"
        } else if (phone.length != 11) {
            registerInfoVerification.flag = false
            registerInfoVerification.message = "手机号码需要11位"
        } else if (passwd.isEmpty()) {
            registerInfoVerification.flag = false
            registerInfoVerification.message = "密码不能为空"
        } else if (passwdAgain.isEmpty()) {
            registerInfoVerification.flag = false
            registerInfoVerification.message = "请再次确认密码"
        } else if (!TextUtils.equals(passwd, passwdAgain)) {
            registerInfoVerification.flag = false
            registerInfoVerification.message = "密码不一致"
        } else {
            registerInfoVerification.flag = true
        }
        return registerInfoVerification
    }
}