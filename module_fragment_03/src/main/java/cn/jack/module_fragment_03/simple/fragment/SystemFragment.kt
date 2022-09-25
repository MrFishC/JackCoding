package cn.jack.module_fragment_03.simple.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.manager.ArouterManager
import cn.jack.library_arouter.BundleParams
import cn.jack.library_arouter.router.RouterPathActivity
import cn.jack.library_arouter.router.RouterPathFragment
import cn.jack.library_util.ext.showToast
import cn.jack.module_fragment_03.R
import cn.jack.module_fragment_03.databinding.FragmentSystemBinding
import cn.jack.module_fragment_03.entity.NavInfo
import cn.jack.module_fragment_03.service.ApiService
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.flexbox.FlexboxLayout
import com.jack.lib_base.base.view.BaseSimpleFragment
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Description: 体系
 *
 * 使用随机布局实现
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_SYSTEM)
open class SystemFragment :
    BaseSimpleFragment<FragmentSystemBinding>(FragmentSystemBinding::inflate) {

//    override fun isRegisterLoadSir(): Boolean {
//        return true
//    }

    private val systemAndSquareInfo =
        MutableStateFlow<EventResult<List<NavInfo>>>(EventResult.OnComplete)

    private val systemAndSquareInfo_ =
        systemAndSquareInfo.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))

    override fun prepareData() {
        super.prepareData()
        httpDataInfo()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                systemAndSquareInfo_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                return@collect
                            }
                            setData(it.data!!)
                        }
                        is EventResult.OnError -> {
                            hideDialog()
                            showToast(it.throwable.message)
                        }
                        is EventResult.OnComplete -> {
                            hideDialog()
                        }
                    }
                }
            }
        }
    }

//    fun dataReload() {
//        httpDataInfo()
//    }

    private fun httpDataInfo() {
        FlowManager.httpRequest<List<NavInfo>> {
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .systemInfoList()
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnError(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            systemAndSquareInfo.value = it
        }.launchIn(lifecycleScope)
    }

    @SuppressLint("InflateParams")
    private fun findItem(): View {
        return layoutInflater.inflate(R.layout.layout_square_item, null, false) as View
    }

    private fun findLabel(flexboxLayout: FlexboxLayout): AppCompatTextView {
        return layoutInflater.inflate(
            R.layout.layout_flexbox_text_item,
            flexboxLayout,
            false
        ) as AppCompatTextView
    }

    private fun setData(data: List<NavInfo>) {
        mBinding.linearContainer.removeAllViews()
        for (i in data.indices) {
            val res = data[i]
            val name = res.name
            val view = findItem()
            val tvTitle = view.findViewById<TextView>(R.id.text_name)
            tvTitle.text = name
            val flexboxLayout = view.findViewById<FlexboxLayout>(R.id.flex_layout)
            val children = res.articles
            for (element in children) {
                val textView = findLabel(flexboxLayout)
                textView.text = element.title
                textView.setOnClickListener {
                    ArouterManager.getInstance().navigationTo(
                        bundleOf(
                            BundleParams.ARTICLE_TITLE to element.title,
                            BundleParams.ARTICLE_ID to element.chapterId
                        ), RouterPathActivity.Subject.PAGER_SUBJECT
                    )
                }
                flexboxLayout.addView(textView)
            }
            mBinding.linearContainer.addView(view)
        }
    }
}