package cn.jack.module_fragment_02.mvvm.ui.fragment

import android.text.TextUtils
import android.view.View
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
import cn.jack.library_util.ContextU
import cn.jack.library_util.KvStoreUtil
import cn.jack.module_fragment_02.databinding.FragmentHome02Binding
import cn.jack.module_fragment_02.entiy.SortInfo
import cn.jack.module_fragment_02.service.ApiService
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSON
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.gyf.immersionbar.ImmersionBar.setTitleBar
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.jack.lib_base.base.view.BaseSimpleFragment
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_wrapper_net.flow.FlowManager
import com.jack.lib_wrapper_net.manager.HttpManager
import com.jack.lib_wrapper_net.model.EventResult
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet.BottomListSheetBuilder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * 1.项目列表
 * 2.项目分类（条件筛选）
 */
@Route(path = RouterPathFragment.HomeSecond.PAGER_HOME_SECOND)
class ModuleFragment02 : BaseSimpleFragment<FragmentHome02Binding>(FragmentHome02Binding::inflate),
    OnRefreshLoadMoreListener,
    OnItemChildClickListener {

    private var mIsRefresh = true

    private val uncollectArticle =
        MutableStateFlow<EventResult<String>>(EventResult.OnComplete)

    private val uncollectArticle_ =
        uncollectArticle.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))


    private val collectAtrticle =
        MutableStateFlow<EventResult<String>>(EventResult.OnComplete)

    private val collectAtrticle_ =
        collectAtrticle.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))

    private val projectInfoList =
        MutableStateFlow<EventResult<ProjectInfoList>>(EventResult.OnComplete)

    private val projectInfoList_ =
        projectInfoList.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))


    private val projectSortInfo =
        MutableStateFlow<EventResult<List<SortInfo>>>(EventResult.OnComplete)

    private val projectSortInfo_ =
        projectSortInfo.shareIn(lifecycleScope, SharingStarted.WhileSubscribed(5000))

    override fun initImmersionBar(view: View) {
        super.initImmersionBar(view)
        immersionBar {
            titleBar(mBinding.projectSortTitleBar)
        }
    }
    override fun prepareData() {
        super.prepareData()
        initAdapter()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uncollectArticle_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
//                            if (it.data == null) {
//                                return@collect
//                            }
                            articleInfo.isCollect = !articleInfo.isCollect
                            mArticleInfoAdapter.notifyItemChanged(position)
                        }
                        is EventResult.OnFail -> {
                            hideDialog()
                            showToast(it.throwable.message)
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectAtrticle_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
//                            if (it.data == null) {
//                                return@collect
//                            }
                            articleInfo.isCollect = !articleInfo.isCollect
                            mArticleInfoAdapter.notifyItemChanged(position)
                        }
                        is EventResult.OnFail -> {
                            hideDialog()
                            showToast(it.throwable.message)
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                projectInfoList_.collect {
                    when (it) {
                        is EventResult.OnStart -> {
                            if (mIsRefresh) {
                                setLayoutState(LayoutState.OnLoading)
                            }
                        }
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                setLayoutState(LayoutState.OnEmpty)
                                return@collect
                            }
                            if (it.data!!.curPage == 0) {
                                mArticleInfoAdapter.setList(it.data!!.datas)
                                setLayoutState(LayoutState.OnSuccess)
                            } else {
                                mArticleInfoAdapter.addData(it.data!!.datas)
                            }
                            mIsRefresh = false
                        }
                        is EventResult.OnFail -> {
//                            hideDialog()
//                            showToast(it.throwable.message)
                            setLayoutState(LayoutState.OnFailed)
                        }
                        is EventResult.OnError -> {
                            setLayoutState(LayoutState.OnNetError)
//                            hideDialog()
//                            showToast(it.throwable.message)
                        }
                        is EventResult.OnComplete -> {
//                            hideDialog()

                        }
                    }
                    mBinding.findSmartRefreshLayout.finishRefresh()
                    mBinding.findSmartRefreshLayout.finishLoadMore()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                projectSortInfo_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                return@collect
                            }

                            showSortDialog(
                                it.data!!, true, true, true,
                                "项目分类", true, false
                            )
                            //将数据存储在本地
                            keepSortInfosInLocal(it.data!!)
                        }
                        is EventResult.OnFail -> {
                            hideDialog()
                            showToast(it.throwable.message)
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

    override fun loadData() {
        super.loadData()
        listProjects(id, mIsRefresh)
    }

    override fun dataReload() {
        mIsRefresh = true
        listProjects(id, mIsRefresh)
    }

    override fun prepareListener() {
        super.prepareListener()
        mBinding.findSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        mBinding.projectSortTitleBar.leftView.visibility = View.GONE
        mBinding.projectSortTitleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View) {}
            override fun onTitleClick(v: View) {}
            override fun onRightClick(v: View) {
                showSortDailog()
            }
        })
        setTargetLoadService(mBinding.findSmartRefreshLayout)
    }

    private fun showSortDailog() {
        //查询本地数据
        val sortJson: String? = KvStoreUtil.getInstance().getString("sort")

        if (!TextUtils.isEmpty(sortJson)) {
            val sortInfos: List<SortInfo> =
                JSON.parseArray<SortInfo>(sortJson, SortInfo::class.java)
            showSortDialog(
                sortInfos, true, true, true,
                "项目分类", true, false
            )
        } else {
            //无数据,请求接口
            sortInfos()
        }
    }

    //将数据存储在本地
    private fun sortInfos() {
        FlowManager.httpRequest<List<SortInfo>> {
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .projectSortInfos()
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            projectSortInfo.value = it
        }.launchIn(lifecycleScope)
    }

    private fun keepSortInfosInLocal(sortInfo: List<SortInfo>) {
        val sortJson: String = JSON.toJSONString(sortInfo)
        KvStoreUtil.getInstance().save("sort", sortJson)
    }

    private fun showSortDialog(
        sortInfos: List<SortInfo>, gravityCenter: Boolean,
        addCancelBtn: Boolean,
        withIcon: Boolean,
        title: CharSequence,
        allowDragDismiss: Boolean,
        withMark: Boolean
    ) {
        val builder = BottomListSheetBuilder(activity)
        builder.setGravityCenter(gravityCenter)
            .setSkinManager(QMUISkinManager.defaultInstance(ContextU.context()))
            .setTitle(title)
            .setAddCancelBtn(addCancelBtn)
            .setAllowDrag(allowDragDismiss)
            .setNeedRightMark(withMark)
            .setOnSheetItemClickListener { dialog, _, position, tag ->
                dialog.dismiss()
                mBinding.projectSortTitleBar.title = sortInfos[position].name
                listProjects(tag.toInt(), true)
            }
        if (withMark) {
            builder.setCheckedIndex(40)
        }
        for (i in sortInfos.indices) {
            val sortInfo: SortInfo = sortInfos[i]
            if (!TextUtils.isEmpty(sortInfo.name) && sortInfo.courseId != 0) {
                builder.addItem(sortInfo.name, "" + sortInfo.id)
            }
        }
        builder.build().show()
    }

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

        mBinding.findRecycleView.adapter = mArticleInfoAdapter
        mArticleInfoAdapter.setOnItemChildClickListener(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        listProjects(cid, mIsRefresh)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mIsRefresh = true
        listProjects(cid, mIsRefresh)
    }

    private var page = 0
    private var cid = 0

    private fun listProjects(id: Int, refresh: Boolean) {
        FlowManager.httpRequest<ProjectInfoList> {
            if (refresh) {
                page = 0
            } else {
                page++
            }
            HttpManager.obtainRetrofitService(ApiService::class.java)
                .listProjects(page, id)
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

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val articleInfo: ArticleInfo = adapter.getItem(position) as ArticleInfo
        if (articleInfo.isCollect) {
            uncollectArticle(articleInfo, position, articleInfo.id)
        } else {
            collectArticle(articleInfo, position, articleInfo.id)
        }
    }

    private fun collectArticle(articleInfo: ArticleInfo, position: Int, id: String) {
        this.articleInfo = articleInfo
        this.position = position

        FlowManager.httpRequest<String> {
            HttpManager.obtainRetrofitService(ApiArticleService::class.java)
                .collectAtrticle(id)
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            collectAtrticle.value = it
        }.launchIn(lifecycleScope)
    }

    private lateinit var articleInfo: ArticleInfo
    private var position by Delegates.notNull<Int>()

    private fun uncollectArticle(articleInfo: ArticleInfo, position: Int, id: String) {
        this.articleInfo = articleInfo
        this.position = position

        FlowManager.httpRequest<String> {
            HttpManager.obtainRetrofitService(ApiArticleService::class.java)
                .unCollectAtrticle(id)
                .map {
                    if (it.errorCode == 0) {
                        EventResult.OnNext(it.data)
                    } else {
                        EventResult.OnFail(Throwable(it.errorMsg))
                    }
                }
        }.onEach {
            uncollectArticle.value = it
        }.launchIn(lifecycleScope)
    }
}