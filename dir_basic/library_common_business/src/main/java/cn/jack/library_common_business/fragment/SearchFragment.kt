package cn.jack.library_common_business.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import cn.jack.library_common_business.R
import cn.jack.library_common_business.databinding.FragmentSearchBinding
import cn.jack.library_weight.flow.FlowAdapter
import com.jack.lib_base.base.view.BaseDialogFragment

/**
 * @创建者 Jack
 * @创建时间 2022/11/14 15:45
 * @描述
 */
class SearchFragment : BaseDialogFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun initParams() {
        super.initParams()
        dialog?.window?.let {
            //背景色白色
            it.setBackgroundDrawable(ColorDrawable(Color.rgb(255, 255, 255)))
            //设置成全屏显示
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            it.setGravity(Gravity.TOP)
        }
    }

    private val mItems = listOf<String>(
        "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
        "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
        "Android", "Weclome Hello", "Button Text", "TextView"
    )

    override fun initData(savedInstanceState: Bundle?) {
        mBinding.flowLayout.setAdapter(object : FlowAdapter<String>(mItems) {
            override fun getView(position: Int, t: String): View {
                val textview: TextView =
                    layoutInflater.inflate(
                        R.layout.mark_text,
                        mBinding.flowLayout,
                        false
                    ) as TextView
                textview.text = t
                return textview
            }
        })
    }

}