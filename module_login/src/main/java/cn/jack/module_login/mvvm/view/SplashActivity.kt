package cn.jack.module_login.mvvm.view

import android.annotation.SuppressLint
import android.os.Handler
import cn.jack.module_login.databinding.ActivitysPlashBinding
import com.jack.lib_base.base.BaseSimpleActivity
import com.jack.lib_wrapper_mvvm.ext.launchActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseSimpleActivity<ActivitysPlashBinding>(ActivitysPlashBinding::inflate) {

    private val mHandler = Handler()

    override fun prepareData() {
        super.prepareData()
        mBinding.ferrisWheelView.startAnimation()
        mHandler.postDelayed({
            launchActivity(LoginActivity::class.java)
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.ferrisWheelView.stopAnimation()
    }
}