package cn.jack.module_login.mvvm.view;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import cn.jack.library_arouter.manager.ArouterManager;
import cn.jack.module_login.R;
import cn.jack.module_login.databinding.ActivitysPlashBinding;
import jack.wrapper.base.mvvm.view.activity.BaseSimpleActiviy;

public class SplashActivity extends BaseSimpleActiviy<ActivitysPlashBinding> {

    private Handler mHandler = new Handler();

    @Override
    protected int setLayoutRes() {
        return R.layout.activitys_plash;
    }

    @Override
    public void prepareData() {
        super.prepareData();

        mBinding.ferrisWheelView.startAnimation();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArouterManager.getInstance().navigation2Login();
                finish();
            }
        },3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.ferrisWheelView.stopAnimation();
    }

}