package cn.jack.lib_common.flutter.base

import android.annotation.SuppressLint
import cn.jack.lib_common.R
import cn.jack.lib_common.databinding.ActivityFlutterBinding
import cn.jack.lib_common.flutter.cache.FlutterCacheManager
import cn.jack.library_util.LogU
import com.jack.lib_base.base.view.BaseSimpleActivity

/**
 * @创建者 Jack
 * @创建时间 2024-04-27 10:25
 * @描述
 */
abstract class FlutterBaseActivity :
    BaseSimpleActivity<ActivityFlutterBinding>(ActivityFlutterBinding::inflate) {

    var mFlutterFragment: UFlutterFragment? = null

    //该位置参数的传递也可以借助arouter来传递
    abstract val moduleName: String?

    override fun perpareWork() {
        super.perpareWork()
        initFragment()
    }

    @SuppressLint("CommitTransaction")
    private fun initFragment() {
        LogU.d("moduleName = $moduleName")
        mFlutterFragment = UFlutterFragment(moduleName!!)
        supportFragmentManager.beginTransaction().add(R.id.root_view, mFlutterFragment!!)
            .commit()
    }

    class UFlutterFragment(private val moduleName: String) : BaseFlutterFragment(moduleName) {

        override fun onDestroy() {
            super.onDestroy()
            //销毁Flutter引擎
            FlutterCacheManager.instance?.destroyCached(moduleName)
        }
    }
}