package cn.jack.module_fragment_03.simple.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.ext.observeInResult
import cn.jack.library_arouter.manager.params.BundleParams
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.module_fragment_03.R
import cn.jack.module_fragment_03.databinding.ModuleFragment03FragmentSquareBinding
import cn.jack.module_fragment_03.entity.SystemInfo
import cn.jack.module_fragment_03.service.ApiService
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.flexbox.FlexboxLayout
import cn.jack.lib_base.base.view.BaseSimpleFragment
import cn.jack.lib_base.uistate.LayoutState
import cn.jack.lib_wrapper_net.flow.FlowManager
import cn.jack.lib_wrapper_net.manager.HttpManager
import cn.jack.lib_wrapper_net.model.EventResult
import com.skydoves.indicatorscrollview.IndicatorAnimation
import com.skydoves.indicatorscrollview.IndicatorItem.Builder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Description: 广场
 */
@Route(path = RouterPathFragment.HomeThird.PAGER_HOME_SQUARE)
open class SquareFragment :
    BaseSimpleFragment<ModuleFragment03FragmentSquareBinding>(ModuleFragment03FragmentSquareBinding::inflate) {

    private val mSystemInfo =
        MutableStateFlow<EventResult<List<SystemInfo>>>(EventResult.OnComplete)

    private val systemAndSquareInfo_ =
        mSystemInfo.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))

    override fun isRegisterLoadSir(): Boolean = true
//    override fun prepareListener() {
//        super.prepareListener()
//        setTargetLoadService(mBinding.indicatorScrollView)
//    }

    override fun prepareData() {
        super.prepareData()

        observeInResult(systemAndSquareInfo_, false) {
            onStart = {
                setLayoutState(LayoutState.OnLoading)
            }
            onSuccess = {
                if (it == null) {
                    setLayoutState(LayoutState.OnEmpty)
                } else {
                    setData(it)
                    setLayoutState(LayoutState.OnSuccess)
                }
            }

            onFail = {
                setLayoutState(LayoutState.OnFailed)
            }

            onError = {
                setLayoutState(LayoutState.OnNetError)
            }
        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                systemAndSquareInfo_.collect {
//                    when (it) {
//                        is EventResult.OnStart -> {
//                            println("OnStart12")
//                            setLayoutState(LayoutState.OnLoading)
//                        }
//
//                        is EventResult.OnNext -> {
//                            if (it.data == null) {
//                                setLayoutState(LayoutState.OnEmpty)
//                                return@collect
//                            }
//                            setData(it.data!!)
//                            setLayoutState(LayoutState.OnSuccess)
//                        }
//
//                        is EventResult.OnFail -> {
////                            hideDialog()
////                            showToast(it.throwable.message)
//                            setLayoutState(LayoutState.OnFailed)
//                        }
//
//                        is EventResult.OnError -> {
////                            hideDialog()
////                            showToast(it.throwable.message)
//                            setLayoutState(LayoutState.OnNetError)
//                        }
//
//                        is EventResult.OnComplete -> {
////                            hideDialog()
//                        }
//                    }
//                }
//            }
//        }
    }

    private var isInitialLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isInitialLoaded) {
            httpDataInfo()
            isInitialLoaded = true
        }
    }

    override fun dataReload() {
        httpDataInfo()
    }

    private fun httpDataInfo() {
        FlowManager.httpRequest<List<SystemInfo>> {
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .squareInfoList()
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            mSystemInfo.value = it
        }.launchIn(lifecycleScope)
    }

    private fun findItem(): View {
        return layoutInflater.inflate(
            R.layout.module_fragment_03_layout_square_item,
            null,
            false
        ) as View
    }

    private fun findLabel(flexboxLayout: FlexboxLayout): AppCompatTextView {
        return layoutInflater.inflate(
            R.layout.module_fragment_03_layout_flexbox_text_item,
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
                    ArouterU.getInstance().navigationTo(
                        RouterPathActivity.Subject.PAGER_SUBJECT, bundleOf(
                            BundleParams.ARTICLE_TITLE to article.name,
                            BundleParams.ARTICLE_ID to article.id
                        )
                    )
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