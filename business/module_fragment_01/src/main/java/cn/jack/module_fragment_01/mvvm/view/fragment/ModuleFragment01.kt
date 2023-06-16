package cn.jack.module_fragment_01.mvvm.view.fragment

import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.lib_common.ext.observeInResult
import cn.jack.lib_common.ext.showToast
import cn.jack.library_arouter.manager.constants.RouterPathActivity
import cn.jack.library_arouter.manager.constants.RouterPathFragment
import cn.jack.library_arouter.manager.params.BundleParams
import cn.jack.library_arouter.manager.router.ArouterU
import cn.jack.library_common_business.entiy.ArticleInfo
import cn.jack.library_util.LogU
import cn.jack.module_fragment_01.R
import cn.jack.module_fragment_01.databinding.ModuleFragment01HomeBinding
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
import cn.jack.module_fragment_01.mvvm.view.adapter.HomeArticleInfoAdapter
import cn.jack.module_fragment_01.mvvm.view.adapter.ImageNetAdapter
import cn.jack.module_fragment_01.mvvm.vm.HomePageOneViewModle
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.jack.lib_base.base.view.BaseFragment
import com.jack.lib_base.uistate.LayoutState
import com.jack.lib_wrapper_net.model.EventResult
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * 1.列表
 * 2.轮播图()
 * 3.搜索
 */

@AndroidEntryPoint
@Route(path = RouterPathFragment.HomeFirst.PAGER_HOME_FIRST)
class ModuleFragment01 :
    BaseFragment<ModuleFragment01HomeBinding, HomePageOneViewModle>(ModuleFragment01HomeBinding::inflate),
    OnRefreshLoadMoreListener, OnItemChildClickListener {
    override val mViewModel: HomePageOneViewModle by viewModels()

    private var mIsRefresh = true

    override fun prepareData() {
        super.prepareData()
        initAdapter()
        mViewModel.loadBinnerInfo()
        mViewModel.loadHomeInfos(mIsRefresh)
    }

    override fun observeViewModel() {
        super.observeViewModel()

        observeInResult(mViewModel.binnerInfos_) {
            onSuccess = {
                showBannerInfos(it!!)
            }
        }

        observeInResult(mViewModel.homeInfos_, false) {
            onStart = {
                if (mIsRefresh) {
                    setLayoutState(LayoutState.OnLoading)
                }
            }

            onSuccess = {
                if (it == null) {
                    mBinding.refreshLayout.finishRefresh()
                    mBinding.refreshLayout.finishLoadMore()
                    setLayoutState(LayoutState.OnEmpty)
                } else {
                    if (mIsRefresh) {
                        mHomeArticleInfoAdapter.setList(it)
                        setLayoutState(LayoutState.OnSuccess)
                    } else {
                        mHomeArticleInfoAdapter.addData(it)
                    }
                }

                mIsRefresh = false
            }

            onFail = {
                setLayoutState(LayoutState.OnFailed)
            }

            onError = {
                setLayoutState(LayoutState.OnNetError)
            }

            onComplete = {
                mBinding.refreshLayout.finishRefresh()
                mBinding.refreshLayout.finishLoadMore()
            }
        }

        observeInResult(mViewModel.collectAtrticle_) {
            onSuccess = {
                articleInfo.collect = !articleInfo.collect
                mHomeArticleInfoAdapter.notifyItemChanged(position)
            }
        }

        observeInResult(mViewModel.uncollectArticle_) {
            onSuccess = {
                articleInfo.collect = !articleInfo.collect
                mHomeArticleInfoAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun showBannerInfos(banInfos: List<BanInfos>) {
        mBanner.setAdapter(ImageNetAdapter(banInfos))
            .addBannerLifecycleObserver(this) //添加生命周期观察者
            .setIndicator(CircleIndicator(context))
            .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
    }

    private lateinit var mHomeArticleInfoAdapter: HomeArticleInfoAdapter
    private fun initAdapter() {
        mHomeArticleInfoAdapter =
            HomeArticleInfoAdapter(R.layout.module_fragment_01_layout_home_article_item)

        mHomeArticleInfoAdapter.setOnItemChildClickListener(this)

        mHomeArticleInfoAdapter.setOnItemClickListener { adapter, _, position ->
            val articleInfo = adapter.data[position] as ArticleInfo
            ArouterU.getInstance().navigationToWithIntercept(
                RouterPathActivity.Web.PAGER_WEB, bundleOf(
                    BundleParams.WEB_URL to articleInfo.link
                )
            )
        }
        mBinding.recyclerView.adapter = mHomeArticleInfoAdapter
        addHeadView()
    }

    private fun addHeadView() {
        val headView = layoutInflater.inflate(
            R.layout.layout_head_view_of_banner_item,
            mBinding.recyclerView.parent as ViewGroup,
            false
        )
        mHomeArticleInfoAdapter.addHeaderView(headView)
        initHeadview(headView)
    }

    private lateinit var mBanner: Banner<List<BanInfos>, ImageNetAdapter>
    private fun initHeadview(headView: View) {
        mBanner = headView.findViewById(R.id.banner)
    }

    override fun dataReload() {
        mIsRefresh = true
        mViewModel.loadHomeInfos(mIsRefresh)
    }

    override fun prepareListener() {
        super.prepareListener()
        mBinding.refreshLayout.setOnRefreshLoadMoreListener(this)
        setTargetLoadService(mBinding.refreshLayout)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mViewModel.loadHomeInfos(mIsRefresh)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mIsRefresh = true
        mViewModel.loadHomeInfos(mIsRefresh)
    }

    private lateinit var articleInfo: ArticleInfo
    private var position by Delegates.notNull<Int>()

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        //判断是否登录
        val articleInfo: ArticleInfo = adapter.getItem(position) as ArticleInfo

        this.articleInfo = articleInfo
        this.position = position + 1 //因为有head的存在，故需要+1

        if (articleInfo.collect) {
            mViewModel.unCollection(articleInfo.id)
        } else {
            mViewModel.collection(articleInfo.id)
        }
    }
}