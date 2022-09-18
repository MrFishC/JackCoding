package cn.jack.module_fragment_01.mvvm.view.fragment

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import cn.jack.library_arouter.router.RouterPathFragment
import cn.jack.library_util.ext.showToast
import cn.jack.module_fragment_01.R
import cn.jack.module_fragment_01.databinding.FragmentHome01Binding
import cn.jack.module_fragment_01.mvvm.model.entity.BanInfos
import cn.jack.module_fragment_01.mvvm.view.adapter.HomeArticleInfoAdapter
import cn.jack.module_fragment_01.mvvm.view.adapter.ImageNetAdapter
import cn.jack.module_fragment_01.mvvm.vm.HomePageOneViewModle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.lib_base.base.BaseFragment
import com.jack.lib_wrapper_net.model.EventResult
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 1.列表
 * 2.轮播图()
 * 3.搜索   参考 ===> https://github.com/Carson-Ho/Search_Layout
 *
 */

@AndroidEntryPoint
@Route(path = RouterPathFragment.HomeFirst.PAGER_HOME_FIRST)
class ModuleFragment01 :
    BaseFragment<FragmentHome01Binding, HomePageOneViewModle>(FragmentHome01Binding::inflate),
    OnRefreshLoadMoreListener {
    override val mViewModel: HomePageOneViewModle by viewModels()

    private var mIsRefresh = false
    override fun isRegisterLoadSir(): Boolean {
        return true
    }

    override fun isViewRegisterLoadSir(): Boolean {
        return true
    }

    override fun prepareData() {
        super.prepareData()
        initAdapter()
//        setLoadService(mBinding.refreshLayout)
        mIsRefresh = true
        mViewModel.loadHomeInfos(true)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.homeInfos_.collect {
                    when (it) {
                        is EventResult.OnStart -> visibleDialog()
                        is EventResult.OnNext -> {
                            if (it.data == null) {
                                showToast("数据为空")
                                mBinding.refreshLayout.finishRefresh()
                                mBinding.refreshLayout.finishLoadMore()
                                return@collect
                            }

                           val articleList = it.data!!.projectInfoList!!.datas
                            if (mIsRefresh) {
                                mHomeArticleInfoAdapter.setList(articleList)
                                showBannerInfos(it.data!!.bannerInfo!!)
                            } else {
                                mHomeArticleInfoAdapter.addData(articleList)
                                showBannerInfos(it.data!!.bannerInfo!!)
                            }
                        }
                        is EventResult.OnError -> {
                            hideDialog()
                            showToast(it.throwable.message)
                        }
                        is EventResult.OnComplete -> {
                            hideDialog()
                        }
                    }

                    mBinding.refreshLayout.finishRefresh()
                    mBinding.refreshLayout.finishLoadMore()
                }
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
        mHomeArticleInfoAdapter = HomeArticleInfoAdapter(R.layout.layout_home_article_item)
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

//    override fun dataReload() {
//        mIsRefresh = true
//        mViewModel.loadHomeInfos(true)
//    }

    override fun prepareListener() {
        super.prepareListener()
        mBinding.refreshLayout.setOnRefreshLoadMoreListener(this)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mIsRefresh = false
        mViewModel.loadHomeInfos(false)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mIsRefresh = true
        mViewModel.loadHomeInfos(true)
    }
}