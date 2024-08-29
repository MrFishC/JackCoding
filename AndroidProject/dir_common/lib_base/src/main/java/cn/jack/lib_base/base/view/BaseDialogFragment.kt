package cn.jack.lib_base.base.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @创建者 Jack
 * @创建时间 2022/11/14 15:19
 * @描述
 */
abstract class BaseDialogFragment<VB : ViewDataBinding>(var block: (LayoutInflater) -> VB) :
    DialogFragment() {

    protected val mBinding: VB by lazy { block(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImmersionBar()
        initParams()
        initData(savedInstanceState)
    }

    private fun initImmersionBar() {
        immersionBar {
            //通用设置
            statusBarColor(cn.jack.lib_base.R.color.transparent)
            statusBarDarkFont(true)
            navigationBarColor(cn.jack.lib_base.R.color.white)
            autoDarkModeEnable(true, 0.2f)
        }
    }

    open fun initParams() {
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.let {
            it.addFlags(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    abstract fun initData(savedInstanceState: Bundle?)
}