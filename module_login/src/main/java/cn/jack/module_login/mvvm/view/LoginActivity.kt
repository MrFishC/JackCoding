package cn.jack.module_login.mvvm.view

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.manager.ArouterManager
import cn.jack.library_arouter.router.RouterPathActivity
import cn.jack.module_login.databinding.ActivityLoginBinding
import cn.jack.module_login.mvvm.vm.LoginViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.BaseActivity
import com.jack.lib_wrapper_net.flow.EventResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 22:38
 * @描述
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
                        is EventResult.OnStart -> println("开始")
                        is EventResult.OnNext -> println("成功")
                        is EventResult.OnError -> println("错误")
                        is EventResult.OnComplete -> println("结束")
                    }
                }
            }
        }
    }

    override fun prepareListener() {
        super.prepareListener()

        //将轻量级的判断直接交给xml
        //点击事件直接在xml中设置
        //databinding的知识 需要进一步学习
        mBinding.btnLoginCommit.setOnClickListener {
            mViewModel.userLogin()
        }

        mBinding.registerText.setOnClickListener {
            ArouterManager.getInstance().navigation2Register()
        }
    }

    //    val observablePhone: Observable<CharSequence> = mBinding.etLoginPhone.textChanges()
//    val observablePassword: Observable<CharSequence> = mBinding.etLoginPassword.textChanges()
//    @SuppressLint("CheckResult")
//    override fun prepareListener() {
//        super.prepareListener()
//        handleViewRepeatClick(mBinding!!.registerText) {
//            ArouterManager.getInstance().navigation2Register()
//        }
//        handleViewRepeatClick(mBinding!!.btnLoginCommit, ViewRepeatClickLisenter {
//            if (mLoginInfoVerification == null) {
//                LogUtils.d("result-> " + "请输入账号密码")
//                return@ViewRepeatClickLisenter
//            }
//            if (mLoginInfoVerification!!.flag) {
//                LogUtils.d("result-> 登陆成功. " + mLoginInfoVerification!!.message)
//                mViewModel!!.userLogin()
//            } else {
//                LogUtils.d("result-> " + mLoginInfoVerification!!.message)
//            }
//        })
//
//        Observable
//            .combineLatest(observablePhone, observablePassword) { charSequence1, charSequence2 ->
//                val result = InfoVerification()
//                if (charSequence1.length == 0) {
//                    result.flag = false
//                    result.message = "手机号码不能为空"
//                } else if (charSequence1.length != 11) {
//                    result.flag = false
//                    result.message = "手机号码需要11位"
//                } else if (charSequence2.length == 0) {
//                    result.flag = false
//                    result.message = "密码不能为空"
//                } else {
//                    result.flag = true
//                }
//                result
//            }
//            .compose(bindToLifecycle())
//            .subscribe { validationResult -> mLoginInfoVerification = validationResult }
//    }
//
//    private var mLoginInfoVerification: InfoVerification? = InfoVerification()
//    override fun prepareParam() {
//        super.prepareParam()
//        mViewModel!!.mPhone.set(SPUtils.getInstance().getData(C.C_USER_NAME, "").toString() + "")
//        mViewModel!!.mPasswd.set(SPUtils.getInstance().getData(C.C_USER_PASSWD, "").toString() + "")
//    } //    @Override
//    //    protected void onCreate(Bundle savedInstanceState) {
//    //        super.onCreate(savedInstanceState);
//    //
//    //        Disposable disposable = Observable.interval(1, TimeUnit.SECONDS)
//    //                .subscribeOn(Schedulers.io())
//    //                .observeOn(AndroidSchedulers.mainThread())
//    //                //AutoDispose的使用就是这句
//    //                //.as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//    //                .subscribe(new Consumer<Long>() {
//    //                    @Override
//    //                    public void accept(Long aLong) throws Exception {
//    //                        LogUtils.i("接收数据,当前线程"+Thread.currentThread().getName(), String.valueOf(aLong));
//    //                    }
//    //                });
//    //
//    //    }
}