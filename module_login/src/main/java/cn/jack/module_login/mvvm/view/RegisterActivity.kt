package cn.jack.module_login.mvvm.view

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.router.RouterPathActivity
import cn.jack.module_login.databinding.ActivityRegisterBinding
import cn.jack.module_login.mvvm.vm.RegisterViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.BaseActivity
import com.jack.lib_wrapper_net.flow.EventResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @创建者 Jack
 * @创建时间 2021/3/22 16:57
 * @描述
 */
@AndroidEntryPoint
@Route(path = RouterPathActivity.Register.PAGER_REGISTER)
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(ActivityRegisterBinding::inflate) {
    override val mViewModel: RegisterViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.registerUserInfo.collect {
                    when (it) {
                        is EventResult.OnStart -> println("开始-注册页面")
                        is EventResult.OnNext -> println("成功-注册页面")
                        is EventResult.OnError -> println("错误-注册页面")
                        is EventResult.OnComplete -> println("结束-注册页面")
                    }
                }
            }
        }
    }

//    private var mRegisterInfoVerification: InfoVerification? = InfoVerification()
//    @SuppressLint("CheckResult")
    override fun prepareListener() {
        super.prepareListener()

        mBinding.registerInfo.setOnClickListener {
            mViewModel.registerUser()
        }

        //使用rxjava结合rxbinding进行校验
//        val observablePhone: Observable<CharSequence> = mBinding!!.etAccount.textChanges()
//        val observablePassword: Observable<CharSequence> = mBinding!!.etPassword.textChanges()
//        val observableAgainPassword: Observable<CharSequence> =
//            mBinding!!.againPassword.textChanges()
//
//        //表单的验证
//        Observable.combineLatest(
//            observablePhone, observablePassword, observableAgainPassword
//        ) { charSequence1, charSequence2, charSequence3 ->
//            val registerInfoVerification = InfoVerification()
//            if (charSequence1.length == 0) {
//                registerInfoVerification.flag = false
//                registerInfoVerification.message = "手机号码不能为空"
//            } else if (charSequence1.length != 11) {
//                registerInfoVerification.flag = false
//                registerInfoVerification.message = "手机号码需要11位"
//            } else if (charSequence2.length == 0) {
//                registerInfoVerification.flag = false
//                registerInfoVerification.message = "密码不能为空"
//            } else if (charSequence3.length == 0) {
//                registerInfoVerification.flag = false
//                registerInfoVerification.message = "请再次确认密码"
//            } else if (!TextUtils.equals(charSequence2, charSequence3)) {
//                registerInfoVerification.flag = false
//                registerInfoVerification.message = "密码不一致"
//            } else {
//                registerInfoVerification.flag = true
//            }
//            registerInfoVerification
//        }
//            .compose(bindToLifecycle())
//            .subscribe { validationResult -> mRegisterInfoVerification = validationResult }
//        handleViewRepeatClick(mBinding!!.registerInfo, ViewRepeatClickLisenter {
//            if (mRegisterInfoVerification == null) {
//                LogUtils.d("result-> " + "请输入注册信息")
//                return@ViewRepeatClickLisenter
//            }
//            if (mRegisterInfoVerification!!.flag) {
//                mViewModel!!.registerUser()
//            } else {
//                LogUtils.d("result-> " + mRegisterInfoVerification!!.message)
//            }
//        })

    }
}