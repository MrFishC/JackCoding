package cn.jack.module_login.mvvm.view;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jakewharton.rxbinding3.widget.RxTextView;
import cn.jack.library_arouter.router.RouterPathActivity;
import cn.jack.library_util.LogUtils;
import cn.jack.module_login.BR;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivityRegisterBinding;
import cn.jack.module_login.mvvm.modle.entity.InfoVerification;
import cn.jack.module_login.mvvm.modle.entity.UserInfo;
import cn.jack.module_login.mvvm.vm.RegisterViewModel;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import jack.wrapper.base.mvvm.view.activity.BaseActivity;
import jack.wrapper.rxhelper.ViewRepeatClickLisenter;

/**
 * @创建者 Jack
 * @创建时间 2021/3/22 16:57
 * @描述
 *
 */
@Route(path = RouterPathActivity.Register.PAGER_REGISTER)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {

    @Override
    public int initContentView() {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void registorUIChangeLiveDataCallBack() {
        super.registorUIChangeLiveDataCallBack();

        mViewModel.navigation2LoginA.observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                //登录页面展示新注册的账号和密码
                openActivity(LoginActivity.class);
                finish();
            }
        });

    }

    private InfoVerification mRegisterInfoVerification = new InfoVerification();

    @SuppressLint("CheckResult")
    @Override
    public void prepareListener() {
        super.prepareListener();

        //使用rxjava结合rxbinding进行校验
        Observable<CharSequence> observablePhone = RxTextView.textChanges(mBinding.etAccount);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(mBinding.etPassword);
        Observable<CharSequence> observableAgainPassword = RxTextView.textChanges(mBinding.againPassword);

        //表单的验证
        Observable.combineLatest(observablePhone, observablePassword, observableAgainPassword,
                new Function3<CharSequence, CharSequence, CharSequence, InfoVerification>() {

                    @NonNull
                    @Override
                    public InfoVerification apply(@NonNull CharSequence charSequence1, @NonNull CharSequence charSequence2,
                                                  @NonNull CharSequence charSequence3) throws Exception {

                        InfoVerification registerInfoVerification = new InfoVerification();
                        if (charSequence1.length() == 0) {
                            registerInfoVerification.flag = false;
                            registerInfoVerification.message = "手机号码不能为空";
                        } else if (charSequence1.length() != 11) {
                            registerInfoVerification.flag = false;
                            registerInfoVerification.message = "手机号码需要11位";
                        } else if (charSequence2.length() == 0) {
                            registerInfoVerification.flag = false;
                            registerInfoVerification.message = "密码不能为空";
                        } else if (charSequence3.length() == 0) {
                            registerInfoVerification.flag = false;
                            registerInfoVerification.message = "请再次确认密码";
                        }else if (!TextUtils.equals(charSequence2,charSequence3)) {
                            registerInfoVerification.flag = false;
                            registerInfoVerification.message = "密码不一致";
                        }else {
                            registerInfoVerification.flag = true;
                        }

                        return registerInfoVerification;
                    }
                })
                .compose(bindToLifecycle())
                .subscribe(new Consumer<InfoVerification>() {
            @Override
            public void accept(InfoVerification validationResult) throws Exception {
                mRegisterInfoVerification = validationResult;
            }
        });

        handleViewRepeatClick(mBinding.registerInfo, new ViewRepeatClickLisenter() {
            @Override
            public void repeatClick() {

                if (mRegisterInfoVerification == null){
                    LogUtils.d( "result-> " + "请输入注册信息");
                    return;
                }

                if (mRegisterInfoVerification.flag) {
                    mViewModel.registerUser();
                } else {
                    LogUtils.d( "result-> " + mRegisterInfoVerification.message);
                }

            }
        });

    }

}
