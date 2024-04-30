package cn.jack.library_common_business.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jack.library_common_business.R
import cn.jack.library_common_business.adapter.HistorySearchInfoAdapter
import cn.jack.library_common_business.database.HistorySearchInfoDatabase
import cn.jack.library_common_business.databinding.FragmentSearchBinding
import cn.jack.library_common_business.entiy.HistorySearchInfo
import cn.jack.library_common_business.entiy.HotSearchBean
import cn.jack.library_common_business.service.ApiArticleService
import cn.jack.library_util.CommonUtil
import cn.jack.library_util.DisplayManager
import cn.jack.library_weight.flow.FlowAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.jack.lib_base.base.view.BaseDialogFragment
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @创建者 Jack
 * @创建时间 2022/11/14 15:45
 * @描述 todo 历史搜素记录去重
 */
class SearchFragment : BaseDialogFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val historySearchAdapter by lazy {
        HistorySearchInfoAdapter()
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun initParams() {
        super.initParams()
        dialog?.window?.let {
            //背景色白色
            it.setBackgroundDrawable(ColorDrawable(Color.rgb(255, 255, 255)))
            //设置成全屏显示
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
            )
            it.setGravity(Gravity.TOP)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                hotSearchInfo_.collect {
                    when (it) {
                        is EventResult.OnStart -> {

                        }
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                return@collect
                            }
                            showHotSearchData(it.data!!)
                        }
                        is EventResult.OnFail -> {

                        }
                        is EventResult.OnError -> {

                        }
                        is EventResult.OnComplete -> {

                        }
                    }
                }
            }
        }

        mBinding.searchTip.searchBackIb.setOnClickListener {
            dismiss()
        }

        mBinding.searchTip.searchTv.setOnClickListener {
            println("搜索开始 ${mBinding.searchTip.searchEdit.text.trim()}")
            //保存搜索历史 + （跳转搜索页面）开始搜索
            saveSearchDataInfo()
        }

        mBinding.searchHistoryClearAllTv.setOnClickListener {
            val allInfos =
                HistorySearchInfoDatabase.getInstance(requireContext()).getHistorySearchDao()
                    .getAll()
            HistorySearchInfoDatabase.getInstance(requireContext()).getHistorySearchDao()
                .deleteAll(allInfos)
            querySearchHistory()
        }

        //设置 适配器   rv_history_search
        //run:适用于let,with函数任何场景。
        mBinding.rvHistorySearch.run {
            layoutManager = linearLayoutManager
            adapter = historySearchAdapter
        }

        historySearchAdapter.run {
            setEmptyView(R.layout.layout_search_empty_view)
            setOnItemClickListener { adapter, _, position ->
                val item = adapter.data[position] as HistorySearchInfo
                //事件
            }

            setOnItemChildClickListener { adapter, _, position ->
                val item = adapter.data[position] as HistorySearchInfo
                //删除指定数据
                HistorySearchInfoDatabase.getInstance(requireContext()).getHistorySearchDao()
                    .delete(item)
                querySearchHistory()
            }
        }

        queryHotSearchData()
    }

    override fun onResume() {
        super.onResume()
        querySearchHistory()
    }

    private fun querySearchHistory() {
        val historyList =
            HistorySearchInfoDatabase.getInstance(requireContext()).getHistorySearchDao().getAll()
        historySearchAdapter.setList(historyList)
    }

    private fun saveSearchDataInfo() {
        val target = mBinding.searchTip.searchEdit.text.trim()
        if (target.isNotEmpty()) {
            val historySearchInfo =
                HistorySearchInfo(target.toString())           //如何才能避免不设置uid       按照这种方式 没有实现去重
            val value1 =
            HistorySearchInfoDatabase.getInstance(requireContext()).getHistorySearchDao()
                .insert(historySearchInfo)

            val value2 =
                HistorySearchInfoDatabase.getInstance(requireContext()).getHistorySearchDao()
                    .insert(historySearchInfo)

            println("内容 $value1 $value2")
        }
    }

    private fun showHotSearchData(hotSearchDatas: MutableList<HotSearchBean>) {
        mBinding.flowLayout.setAdapter(object : FlowAdapter<HotSearchBean>(hotSearchDatas) {
            override fun getView(position: Int, t: HotSearchBean?): View {
                val textview: TextView = layoutInflater.inflate(
                    R.layout.mark_text, mBinding.flowLayout, false
                ) as TextView

                val padding: Int = DisplayManager.dip2px(10F)
                textview.setPadding(padding, padding, padding, padding)
                textview.text = t?.name
                textview.setTextColor(CommonUtil.randomColor())
                return textview
            }
        })
    }

    private val mHotSearchInfo =
        MutableStateFlow<EventResult<MutableList<HotSearchBean>>>(EventResult.OnComplete)

    private val hotSearchInfo_ =
        mHotSearchInfo.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))

    //查询热门搜索
    private fun queryHotSearchData() {
        FlowManager.httpRequestSimple<MutableList<HotSearchBean>>(
            HttpManager.obtainRetrofitService(ApiArticleService::class.java).getHotSearchData()
        ).onEach {
            mHotSearchInfo.value = it
        }.launchIn(lifecycleScope)
    }

}