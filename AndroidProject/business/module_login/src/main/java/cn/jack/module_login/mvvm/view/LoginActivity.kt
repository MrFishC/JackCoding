package cn.jack.module_login.mvvm.view

import android.annotation.SuppressLint
import androidx.activity.viewModels
import cn.jack.lib_common.ext.observeInResult
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_common_business.constant.C
import cn.jack.library_util.KvStoreUtil
import cn.jack.module_login.databinding.ModuleLoginActivityLoginBinding
import cn.jack.module_login.mvvm.TaskStartUp
import cn.jack.module_login.mvvm.modle.entity.InfoVerification
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.vm.LoginViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.view.BaseActivity
import com.jakewharton.rxbinding3.widget.textChanges
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 22:38
 * @描述 Rxjava表单验证（使用combineLatest操作符）可以抽离出来作为Rxjava框架的常用点
 */
@AndroidEntryPoint
@Route(path = RouterPathActivity.Login.PAGER_LOGIN)
class LoginActivity :
    BaseActivity<ModuleLoginActivityLoginBinding, LoginViewModel>(ModuleLoginActivityLoginBinding::inflate) {
    override val mViewModel: LoginViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()

        TaskStartUp.start()

        observeInResult(mViewModel.userInfo) {
            onSuccess = {
                openHome(it)
            }
        }
    }

    private fun openHome(data: UserInfo?) {
        KvStoreUtil.getInstance().save(C.Login.user_name, data?.email)
        mBinding.btnLoginCommit.reset()
        ArouterU.getInstance().navigationTo(
            RouterPathActivity.Home.PAGER_HOME
        )
//        ARouter.getInstance().build(RouterPathActivity.Home.PAGER_HOME).navigation()
        finish()
    }

    override fun prepareData() {
        super.prepareData()
        mBinding.viewModel = mViewModel
        mViewModel.mPhone.set(KvStoreUtil.getInstance().getString(C.C_USER_NAME))
        mViewModel.mPasswd.set(KvStoreUtil.getInstance().getString(C.C_USER_PASSWD))
    }

    @SuppressLint("CheckResult")
    override fun prepareListener() {
        super.prepareListener()

        /*表单验证-非简化代码*/
//        Observable
//            .combineLatest(
//                mBinding.etLoginPhone.textChanges(),
//                mBinding.etLoginPassword.textChanges(),
//                object : BiFunction<CharSequence, CharSequence, InfoVerification> {
//                    override fun apply(
//                        phone: CharSequence,
//                        passwd: CharSequence
//                    ): InfoVerification {
//                        return isValidInfo(phone, passwd)
//                    }
//                }
//            )
//            .autoDispose(AndroidLifecycleScopeProvider.from(this))
//            .subscribe(object : Consumer<InfoVerification> {
//                override fun accept(it: InfoVerification) {
//                    mBinding.btnLoginCommit.isEnabled = it.flag
//                }
//            })

        Observable.combineLatest(
            mBinding.etLoginPhone.textChanges(),
            mBinding.etLoginPassword.textChanges()
        ) { phone, passwd -> isValidInfo(phone, passwd) }
            .autoDispose(AndroidLifecycleScopeProvider.from(this))
            .subscribe { it -> mBinding.btnLoginCommit.isEnabled = it.flag }

        mBinding.btnLoginCommit.setOnClickListener {
            mViewModel.userLogin(
                mViewModel.mPhone.get()!!,
                mViewModel.mPasswd.get()!!
            )
        }

        mBinding.registerText.setOnClickListener {
            ArouterU.getInstance().navigationTo(
                RouterPathActivity.Register.PAGER_REGISTER
            )
        }
    }

    /*验证的逻辑在这里统一处理*/
    private fun isValidInfo(phone: CharSequence, passwd: CharSequence): InfoVerification {
        val result = InfoVerification()
        if (phone.isEmpty()) {
            result.flag = false
            result.message = "手机号码不能为空"
        } else if (passwd.isEmpty()) {
            result.flag = false
            result.message = "密码不能为空"
        } else if (phone.length != 11) {
            result.flag = false
            result.message = "手机号码需要11位"
        } else {
            result.flag = true
        }
        return result
    }

}