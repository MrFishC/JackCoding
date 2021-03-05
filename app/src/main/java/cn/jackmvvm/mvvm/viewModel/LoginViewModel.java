package cn.jackmvvm.mvvm.viewModel;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import cn.jack.library_util.AppContext;
import cn.jackmvvm.mvvm.model.repository.ILoginHttpDataSource;
import cn.jackmvvm.mvvm.model.repository.ILoginLisenter;
import cn.jackmvvm.mvvm.model.repository.LoginRepository;
import jack.wrapper.base.mvvm.viewModel.BaseViewModel;


/**
 * created by Jack
 * email:yucrun@163.com
 * describe:
 *
 */

public class LoginViewModel extends BaseViewModel<LoginRepository> implements ILoginLisenter {

    //帐号的绑定
    public ObservableField<String> mUserName = new ObservableField<>();
    //密码的绑定
    public ObservableField<String> mPasswd   = new ObservableField<>();

    private ILoginHttpDataSource mILoginHttpDataSource;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LoginViewModel(@NonNull Application application, @NonNull LoginRepository model) {
        super(application, model);
        mILoginHttpDataSource = model;
    }

    /**
     * 登录
     */
    private void login() {
        mILoginHttpDataSource.login(mUserName.get(),mPasswd.get(),this);
    }

    //用户的点击事件直接被回调到ViewModel层了，更好的维护了业务逻辑
    public View.OnClickListener loginOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    //清除帐号的点击事件
    public View.OnClickListener clearUserNameOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //清除用户名
            mUserName.set("");
        }
    };

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void closeLoadDialog() {
        dismissDialog();
    }

    @Override
    public void openMainActivtiy() {
        Toast.makeText(AppContext.getContext(),"跳转页面",Toast.LENGTH_SHORT).show();
    }

}
