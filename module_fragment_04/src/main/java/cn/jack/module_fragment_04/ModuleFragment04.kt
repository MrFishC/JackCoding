package cn.jack.module_fragment_04

import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.ext.showToast
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.library_arouter.manager.params.BundleParams
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_common_business.adapter.ArticleInfoAdapter
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.library_common_business.entiy.ProjectInfoList
import cn.jack.library_common_business.service.ApiArticleService
import cn.jack.module_fragment_04.databinding.FragmentHome04Binding
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.view.BaseSimpleFragment
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 1.（搜藏）文章列表
 * 2.右上角添加设置入口
 *
 * 3.滑动删除
 */
@Route(path = RouterPathFragment.HomeFour.PAGER_HOME_FOUR)
class ModuleFragment04 : BaseSimpleFragment<FragmentHome04Binding>(FragmentHome04Binding::inflate),
    OnRefreshLoadMoreListener {

    private lateinit var mArticleInfoAdapter: ArticleInfoAdapter
    private fun initAdapter() {
        mArticleInfoAdapter = ArticleInfoAdapter()

        mArticleInfoAdapter.setOnItemClickListener { adapter, _, position ->
            val articleInfo = adapter.data[position] as ArticleInfo
            ArouterU.getInstance().navigationTo(
                RouterPathActivity.Web.PAGER_WEB, bundleOf(
                    BundleParams.WEB_URL to articleInfo.link
                )
            )
        }

        mBinding.collectRecycleView.adapter = mArticleInfoAdapter
    }

    override fun prepareData() {
        super.prepareData()
        mBinding.collectSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        initAdapter()
//        setLoadService(mBinding.collectSmartRefreshLayout)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                projectInfoList_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                return@collect
                            }
                            val datas = it.data!!.datas
                            for (articleBean in datas) {
                                articleBean.isCollect = true
                            }

                            if (page == 0) {
                                mArticleInfoAdapter.setList(datas)
                            } else {
                                mArticleInfoAdapter.addData(datas)
                            }
                        }
                        is EventResult.OnFail -> {
                            hideDialog()
                            showToast(it.throwable.message)
                            mBinding.collectSmartRefreshLayout.finishRefresh()
                            mBinding.collectSmartRefreshLayout.finishLoadMore()
                        }
                        is EventResult.OnError -> {
                            hideDialog()
                            showToast(it.throwable.message)
                            mBinding.collectSmartRefreshLayout.finishRefresh()
                            mBinding.collectSmartRefreshLayout.finishLoadMore()
                        }
                        is EventResult.OnComplete -> {
                            hideDialog()
                            mBinding.collectSmartRefreshLayout.finishRefresh()
                            mBinding.collectSmartRefreshLayout.finishLoadMore()
                        }
                    }
                }
            }
        }
    }

    override fun prepareListener() {
        super.prepareListener()
//        mBinding.collect.titleBar.getTitleView().setText("我的收藏")
//        mBinding.collect.titleBar.getLeftView().setVisibility(View.INVISIBLE)
    }

    override fun loadData() {
        super.loadData()
        listMyCollect(true)
    }

//    fun dataReload() {
//        super.dataReload()
//        listMyCollect(true)
//    }

    private val projectInfoList =
        MutableStateFlow<EventResult<ProjectInfoList>>(EventResult.OnComplete)

    private val projectInfoList_ =
        projectInfoList.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))


    private var page = 0

    //    private var mCollectionArticleInfos: RxBaseSimpleSubscriber<ProjectInfoList>? = null
    private fun listMyCollect(refresh: Boolean) {
        if (refresh) {
            page = 0
        } else {
            page++
        }

        FlowManager.httpRequest<ProjectInfoList> {
            HttpManager.obtainRetrofitService(ApiArticleService::class.java)
                .getArticleCollection(page)
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            projectInfoList.value = it
        }.launchIn(lifecycleScope)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        listMyCollect(false)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        listMyCollect(true)
    }

}