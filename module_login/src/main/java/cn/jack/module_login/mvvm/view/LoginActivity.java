package cn.jack.module_login.mvvm.view;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jakewharton.rxbinding3.widget.RxTextView;

import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_common_business.constant.C;
import cn.jack.library_util.AppContext;
import cn.jack.library_util.LogUtils;
import cn.jack.library_util.SPUtils;
import cn.jack.module_login.BR;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivityLoginBinding;
import cn.jack.module_login.mvvm.modle.entity.InfoVerification;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.vm.LoginViewModel;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;
import jack.wrapper.rxhelper.ViewRepeatClickLisenter;

/**
 * @创建者 Jack
 * @创建时间 2021/3/16 22:38
 * @描述
 */
@Route(path = RouterPathActivity.Login.PAGER_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        return new LoginViewModel(AppContext.getApplication());
    }

    @Override
    protected void registorUIChangeLiveDataCallBack() {
        super.registorUIChangeLiveDataCallBack();
        mViewModel.navigation2HomeA.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                ArouterManager.getInstance().navigation2Home();
                finish();
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void prepareListener() {
        super.prepareListener();

        handleViewRepeatClick(mBinding.registerText, new ViewRepeatClickLisenter() {
            @Override
            public void repeatClick() {
                ArouterManager.getInstance().navigation2Register();
            }
        });

        handleViewRepeatClick(mBinding.btnLoginCommit, new ViewRepeatClickLisenter() {
            @Override
            public void repeatClick() {

                if (mLoginInfoVerification == null){
                    LogUtils.d( "result-> " + "请输入账号密码");
                    return;
                }
                if (mLoginInfoVerification.flag) {
                    LogUtils.d("result-> 登陆成功. " + mLoginInfoVerification.message);
                    mViewModel.userLogin();
                } else {
                    LogUtils.d( "result-> " + mLoginInfoVerification.message);
                }

            }
        });

        Observable<CharSequence> observablePhone = RxTextView.textChanges(mBinding.etLoginPhone);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(mBinding.etLoginPassword);

        Observable
                .combineLatest(observablePhone, observablePassword, new BiFunction<CharSequence, CharSequence, InfoVerification>() {
                    @Override
                    public @NonNull
                    InfoVerification apply(@NonNull CharSequence charSequence1, @NonNull CharSequence charSequence2) throws Exception {

                        InfoVerification result = new InfoVerification();

                        if (charSequence1.length() == 0) {
                            result.flag = false;
                            result.message = "手机号码不能为空";
                        } else if (charSequence1.length() != 11) {
                            result.flag = false;
                            result.message = "手机号码需要11位";
                        } else if (charSequence2.length() == 0) {
                            result.flag = false;
                            result.message = "密码不能为空";
                        }else {
                            result.flag = true;
                        }

                        return result;

                    }
                })
                .compose(bindToLifecycle())
                .subscribe(new Consumer<InfoVerification>() {
            @Override
            public void accept(InfoVerification validationResult) throws Exception {
                mLoginInfoVerification = validationResult;
            }
        });

    }

    private InfoVerification mLoginInfoVerification = new InfoVerification();

    @Override
    public void prepareParam() {
        super.prepareParam();
        mViewModel.mPhone.set(SPUtils.getInstance().getData(C.C_USER_NAME,"") + "");
        mViewModel.mPasswd.set(SPUtils.getInstance().getData(C.C_USER_PASSWD,"") + "");
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Disposable disposable = Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                //AutoDispose的使用就是这句
//                //.as(AutoDispose.<Long>autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        LogUtils.i("接收数据,当前线程"+Thread.currentThread().getName(), String.valueOf(aLong));
//                    }
//                });
//
//    }

}
