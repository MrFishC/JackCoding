package cn.jack.module_login.mvvm.view

import android.annotation.SuppressLint
import cn.jack.module_login.databinding.ModuleLoginActivitysPlashBinding
import com.jack.lib_base.base.view.BaseSimpleActivity
import com.jack.lib_base.ext.launchActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseSimpleActivity<ModuleLoginActivitysPlashBinding>(ModuleLoginActivitysPlashBinding::inflate) {
    override fun prepareData() {
        super.prepareData()

        mBinding.ferrisWheelView.startAnimation()
        postDelayed({
            launchActivity(LoginActivity::class.java)
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.ferrisWheelView.stopAnimation()
    }
}