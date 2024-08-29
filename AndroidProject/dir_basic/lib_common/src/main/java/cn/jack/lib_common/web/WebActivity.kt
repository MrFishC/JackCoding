package cn.jack.lib_common.web

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import cn.jack.lib_common.R
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.params.BundleParams
import com.alibaba.android.arouter.facade.annotation.Route
import cn.jack.library_webview.fragment.BaseH5Fragment

/**
 * @创建者 Jack
 * @创建时间 2022/9/20 1:59
 * @描述
 */
@Route(path = RouterPathActivity.Web.PAGER_WEB)
class WebActivity : AppCompatActivity() {

//    @JvmField
//    @Autowired
//    var webUrl: String? = null
// 不使用Bundle传递参数的话，需要用Autowired接收数据，同时要添加ARouter.getInstance().inject(this)，并且跨进程时还有新的问题，故推荐使用bundle的方式传值

    var mBaseH5Fragment: cn.jack.library_webview.fragment.BaseH5Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common_web)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.web_view_fragment, cn.jack.library_webview.fragment.BaseH5Fragment.newInstance(
                intent.extras!!.getString(BundleParams.WEB_URL)
            )
        ).commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mBaseH5Fragment != null) {
            mBaseH5Fragment!!.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }
}