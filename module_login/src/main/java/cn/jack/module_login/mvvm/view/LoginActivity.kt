package cn.jack.module_login.mvvm.view

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.ext.showToast
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_common_business.constant.C
import cn.jack.library_util.KvStoreUtil
import cn.jack.module_login.databinding.ActivityLoginBinding
import cn.jack.module_login.mvvm.modle.entity.InfoVerification
import cn.jack.module_login.mvvm.modle.entity.UserInfo
import cn.jack.module_login.mvvm.vm.LoginViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.view.BaseActivity
import com.jack.lib_wrapper_net.model.EventResult
import com.jakewharton.rxbinding3.widget.textChanges
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import kotlinx.coroutines.launch

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 22:38
 * @描述
 *
 *  表单验证，使用Rxjava的combineLatest操作符
 */
@AndroidEntryPoint
@Route(path = RouterPathActivity.Login.PAGER_LOGIN)
class LoginActivity :
    BaseActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::inflate) {
    override val mViewModel: LoginViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()

        //可重启生命周期感知型协程 https://developer.android.com/topic/libraries/architecture/coroutines#restart
        // Create a new coroutine in the lifecycleScope
        lifecycleScope.launch {
            //StateFlow和SharedFlow是热流，热流不会随着生命周期自动取消，也就是说页面消失后还会继续监听数据，或者下次进入时候会重复绑定，这样就会导致很多无法预料的错误。
            //观察StateFlow需要在协程中，一般我们会使用下面几种
            //1、lifecycleScope.launch: 立即启动协程，并且在本 Activity或Fragment 销毁时结束协程。
            //2、LaunchWhenStarted 和 LaunchWhenResumed:它会在lifecycleOwner进入X状态之前一直等待，又在离开X状态时挂起协程
            //StateFlow 或任意其他数据流收集数据的操作并不会停止，所以官方推荐repeatOnLifecycle来构建协程。
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.userInfo.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> openHome(it.data)
                        is EventResult.OnFail -> {
                            hideDialog()
                            mBinding.btnLoginCommit.reset()
                            showToast(it.throwable.message)
                        }
                        is EventResult.OnError -> {
                            hideDialog()
                            mBinding.btnLoginCommit.reset()
                            showToast(it.throwable.message)
                        }
                        is EventResult.OnComplete -> {
                            mBinding.btnLoginCommit.reset()
                            hideDialog()
                        }
                    }
                }
            }
        }
    }

    private fun openHome(data: UserInfo?) {
        KvStoreUtil.getInstance().save(C.Login.user_name, data?.email)
        mBinding.btnLoginCommit.reset()
//        ArouterManager.getInstance().navigation2Home()
        ArouterU.getInstance().navigationTo(
            RouterPathActivity.Home.PAGER_HOME
        )
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
//            ArouterManager.getInstance().navigation2Register()
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