package cn.jack.module_fragment_03.simple.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.manager.ArouterManager
import cn.jack.library_arouter.router.RouterPathFragment
import cn.jack.library_util.ext.showToast
import cn.jack.module_fragment_03.R
import cn.jack.module_fragment_03.databinding.FragmentSquareBinding
import cn.jack.module_fragment_03.entity.SystemInfo
import cn.jack.module_fragment_03.service.ApiService
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.flexbox.FlexboxLayout
import com.jack.lib_base.base.BaseSimpleFragment
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import com.skydoves.indicatorscrollview.IndicatorAnimation
import com.skydoves.indicatorscrollview.IndicatorItem.Builder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Description: 广场
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_SQUARE)
open class SquareFragment : BaseSimpleFragment<FragmentSquareBinding>(FragmentSquareBinding::inflate) {

//    override fun isRegisterLoadSir(): Boolean {
//        return true
//    }

    private val mSystemInfo =
        MutableStateFlow<EventResult<List<SystemInfo>>>(EventResult.OnComplete)

    private val systemAndSquareInfo_ =
        mSystemInfo.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))


    override fun prepareData() {
        super.prepareData()
        httpDataInfo()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                systemAndSquareInfo_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
                            if(it.data == null){
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
        FlowManager.httpRequest<List<SystemInfo>> {
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .squareInfoList()
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnError(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            mSystemInfo.value = it
        }.launchIn(lifecycleScope)
    }

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

    @SuppressLint("ResourceAsColor")
    private fun setData(data: List<SystemInfo>) {
        mBinding.linearContainer.removeAllViews()
        for (i in data.indices) {
            val res = data[i]
            val name = res.name
            val view = findItem()
            val tvTitle = view.findViewById<TextView>(R.id.text_name)
            tvTitle.text = name
            val flexboxLayout = view.findViewById<FlexboxLayout>(R.id.flex_layout)
            val articles = res.children
            for (article in articles) {
                val textView = findLabel(flexboxLayout)
                textView.text = article.name
                flexboxLayout.addView(textView)
                textView.setOnClickListener {
                    ArouterManager.getInstance().navigation2Web(article.lisenseLink)
                }
            }

            mBinding.linearContainer.addView(view)
            mBinding.indicatorView.addIndicatorItem(
                Builder(view)
                    .setItemColor(R.color.color_9b9b9b)
                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
                    .build()
            )
        }
    }
}