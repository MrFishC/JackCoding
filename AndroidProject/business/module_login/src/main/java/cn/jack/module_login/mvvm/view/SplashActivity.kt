package cn.jack.module_login.mvvm.view

import android.annotation.SuppressLint
import cn.jack.module_login.databinding.ModuleLoginActivitysPlashBinding
import com.jack.lib_base.base.view.BaseSimpleActivity
import com.jack.lib_base.ext.launchActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseSimpleActivity<ModuleLoginActivitysPlashBinding>(ModuleLoginActivitysPlashBinding::inflate) {
    override fun prepareData() {
        super.prepareData()

//        mBinding.ferrisWheelView.startAnimation()
        startAnimation()

//        val duration: Long = mBinding.splashAnimation.duration

        postDelayed({
            launchActivity(LoginActivity::class.java)
            finish()
        }, 3000)
    }

    private fun startAnimation() {
//        mBinding.splashAnimation.setAnimation("splash_loading.json")
        mBinding.splashAnimation.setAnimation("lottie_animation.json")
//        mBinding.splashAnimation.addAnimatorListener(object : AnimatorListener {
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                println("onAnimationEnd ")
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//        })
        mBinding.splashAnimation.playAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
//        mBinding.ferrisWheelView.stopAnimation()

        //参考：https://juejin.cn/post/6890861185953251341
        //在测试过程中，发现 animation.cancelAnimation()，最终也会回调 onAnimationEnd这个方法。
        //读过源码之后，发现确实是在cancel之后回调了animationEnd的方法。
        mBinding.splashAnimation.cancelAnimation()
    }
}