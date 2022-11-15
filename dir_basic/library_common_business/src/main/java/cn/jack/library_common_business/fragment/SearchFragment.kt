package cn.jack.library_common_business.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import cn.jack.library_common_business.databinding.FragmentSearchBinding
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

    override fun initData(savedInstanceState: Bundle?) {

    }

}