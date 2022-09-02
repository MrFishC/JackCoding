package cn.jack.module_login.mvvm.view

import android.annotation.SuppressLint
import android.os.Handler
import cn.jack.library_arouter.manager.ArouterManager
import cn.jack.module_login.databinding.ActivitysPlashBinding
import com.jack.lib_base.base.BaseSimpleActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseSimpleActivity<ActivitysPlashBinding>(ActivitysPlashBinding::inflate) {

    private val mHandler = Handler()

    override fun prepareData() {
        super.prepareData()
        mBinding.ferrisWheelView.startAnimation()
        mHandler.postDelayed({
            ArouterManager.getInstance().navigation2Login()
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.ferrisWheelView.stopAnimation()
    }
}