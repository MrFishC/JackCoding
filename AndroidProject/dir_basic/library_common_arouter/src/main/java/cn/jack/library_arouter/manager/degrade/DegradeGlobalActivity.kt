package cn.jack.library_arouter.manager.degrade

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.jack.library_arouter.R
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.empty.EmptyView
import cn.jack.library_arouter.manager.icfont.IconFontTextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 全局统一错误页
 */
@Route(path = RouterPathActivity.Global.PAGER_GLOBAL)
class DegradeGlobalActivity : AppCompatActivity() {
    @JvmField
    @Autowired
    var degrade_title: String? = null
    @JvmField
    @Autowired
    var degrade_desc: String? = null
    @JvmField
    @Autowired
    var degrade_action: String? = null

    private lateinit var action_back: IconFontTextView
    private lateinit var empty_view: EmptyView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_global_degrade)

        ARouter.getInstance().inject(this)

        action_back = findViewById(R.id.action_back)
        empty_view = findViewById(R.id.empty_view)

        empty_view.setIcon(R.string.if_empty)

        if (degrade_title != null) {
            empty_view.setTitle(degrade_title!!)
        }

        if (degrade_desc != null) {
            empty_view.setDesc(degrade_desc!!)
        }

        if (degrade_action != null) {
            empty_view.setHelpAction(listener = View.OnClickListener {
                var intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(degrade_action))
                startActivity(intent)
            })
        }

        action_back.setOnClickListener { onBackPressed() }
    }
}